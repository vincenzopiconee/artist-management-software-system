# Artist Management System (AMS)

A robust, enterprise-style Java desktop application engineered for musical artist booking agency management. Designed and implemented following the Unified Process, Boundary-Control-Entity (BCE) architectural pattern, and the Data Access Object (DAO) design pattern, backed by a relational MySQL database.

---

## Table of Contents

- [1. Executive Summary](#1-executive-summary)
- [2. Key Features](#2-key-features)
- [3. Architecture and Design Patterns](#3-architecture-and-design-patterns)
  - [3.1 Boundary-Control-Entity (BCE) Architecture](#31-boundary-control-entity-bce-architecture)
  - [3.2 Data Access Object (DAO) Pattern](#32-data-access-object-dao-pattern)
  - [3.3 Singleton Pattern](#33-singleton-pattern)
  - [3.4 Object-Oriented Domain Modeling](#34-object-oriented-domain-modeling)
- [4. Domain Rules and Event Constraints](#4-domain-rules-and-event-constraints)
  - [4.1 Musician Specializations](#41-musician-specializations)
  - [4.2 Event Typologies and Composition Rules](#42-event-typologies-and-composition-rules)
  - [4.3 Booking and Summons Lifecycle](#43-booking-and-summons-lifecycle)
  - [4.4 Automated Monthly Financial Reporting](#44-automated-monthly-financial-reporting)
- [5. Database Architecture](#5-database-architecture)
- [6. Project Structure](#6-project-structure)
- [7. UML and Engineering Artifacts](#7-uml-and-engineering-artifacts)
- [8. Testing and Quality Assurance](#8-testing-and-quality-assurance)
  - [8.1 Functional Testing (Black-Box)](#81-functional-testing-black-box)
  - [8.2 Structural Testing (White-Box)](#82-structural-testing-white-box)
- [9. Getting Started](#9-getting-started)
  - [9.1 Prerequisites](#91-prerequisites)
  - [9.2 Database Setup](#92-database-setup)
  - [9.3 Building with Maven](#93-building-with-maven)
  - [9.4 Running the Application](#94-running-the-application)
  - [9.5 Running the Automated Tests](#95-running-the-automated-tests)
- [10. Academic Context and Credits](#10-academic-context-and-credits)

---

## 1. Executive Summary

The Artist Management System is an event planning and booking management solution developed for artistic agencies that manage musical talent. The system orchestrates communication between the agency manager (the system administrator) and affiliated musicians (singers and instrumentalists).

The software provides full lifecycle support for:
- Querying musician real-time availability across target dates.
- Scheduling musical events while strictly enforcing domain rules on musician ensembles.
- Issuing convocations (booking invitations) and tracking acceptances/rejections.
- Automatically transitioning event state to confirmed once all participants accept.
- Generating monthly summaries of confirmed events and liquidating musician compensation.

The project was engineered with high academic rigor for a 10 ECTS Software Engineering curriculum, covering requirements elicitation, domain analysis, cost estimation (Function Points / COCOMO), structural and functional test planning, and UML modeling.

---

## 2. Key Features

- **Musician Directory Management:** Comprehensive profiles for singers and instrumentalists with contact information, individual cachet per performance, vocal ranges, and instrument proficiencies.
- **Dynamic Availability Tracking:** Musicians manage their own active booking calendar by submitting availability periods.
- **Rule-Enforced Event Scheduling:** Multi-step event creation wizard with validation for future dates, venue length limits, and ensemble composition constraints.
- **Real-Time Cost Calculation:** Automatic aggregation of individual artist fees to compute total event overhead for the agency.
- **Automated Workflow Engine:** State-machine-based summons management where an event shifts from `IN_ATTESA` (pending) to `CONFERMATO` (confirmed) upon unanimous confirmation.
- **Simulated Batch Monthly Reporting:** Generates audit reports on confirmed events and calculates total monthly compensation owed to each artist.
- **Role-Based Graphical User Interface:** Java Swing user interfaces partitioned between the Agency Manager and the Musician portal, plus a dedicated simulation driver for temporal testing.

---

## 3. Architecture and Design Patterns

The system adheres strictly to the classic three-tier Boundary-Control-Entity (BCE) architecture, ensuring clean separation of presentation, business logic, and persistent storage.

```
+-------------------------------------------------------------+
|                       Boundary Layer                        |
|   (Swing GUIs: IMain, IGestore, GUILoginMusicista, etc.)   |
+-------------------------------------------------------------+
                              |
                              v
+-------------------------------------------------------------+
|                        Control Layer                        |
|                  (GestioneEventi Singleton)                 |
+-------------------------------------------------------------+
               |                               |
               v                               v
+-----------------------------+ +-----------------------------+
|        Entity Layer         | |       Database / DAO        |
|  (Musicista, Evento, etc.)  | |   (DBManager, DAOs, MySQL)  |
+-----------------------------+ +-----------------------------+
```

### 3.1 Boundary-Control-Entity (BCE) Architecture

- **Boundary (`src/main/java/boundary`):** Handles all visual interactions with end-users. Implemented with Java Swing and AWT. Boundary classes capture user input, display dialogs, and route actions directly to the control layer without directly holding business validation logic.
- **Control (`src/main/java/control`):** Represented by the central `GestioneEventi` controller. It orchestrates user workflows, executes domain validation, performs ensemble rule verification, triggers notification handlers, and coordinates persistence via DAOs.
- **Entity (`src/main/java/entity`):** Pure domain model containing state and object-level operations. Models the core business entities: `Musicista`, `Cantante`, `Strumentista`, `Evento`, `Concerto`, `Esibizione`, and `Strumentale`.

### 3.2 Data Access Object (DAO) Pattern

Persistence logic is completely decoupled from domain entities using the DAO design pattern:
- `DBManager`: Manages JDBC connection pooling and lifecycle to MySQL.
- `MusicistaDAO`: Encapsulates CRUD operations, availability queries, credential checks, and monthly compensation queries.
- `EventoDAO`: Encapsulates transaction logic for creating events, updating convocation statuses, decrementing pending musician counts, and aggregating monthly event listings.

### 3.3 Singleton Pattern

The controller `GestioneEventi` implements the GoF Singleton pattern via a private constructor and a synchronized accessor `getInstance()`. This guarantees a single entry point for orchestrating agency workflows and prevents conflicting transactional states.

### 3.4 Object-Oriented Domain Modeling

Polymorphism and inheritance are applied across the domain:
- `Musicista` serves as an abstract base entity extended by `Cantante` and `Strumentista`.
- `Evento` serves as an abstract base entity specialized by `Concerto`, `Esibizione`, and `Strumentale`, each mapping to distinct ensemble constraints.

---

## 4. Domain Rules and Event Constraints

### 4.1 Musician Specializations

1. **Common Attributes:** Every musician has an identifier, first name, last name, unique email, password, telephone number, and standard fee (cachet in EUR).
2. **Singers (`Cantante`):**
   - Musical genre specialization: Classical, Pop, Light, Jazz.
   - For Classical singers, the vocal range is tracked: Soprano, Mezzosoprano, Contralto, Tenore, Baritono, Basso.
3. **Instrumentalists (`Strumentista`):**
   - Specialized in up to 4 distinct instruments (flute, oboe, violin, cello, piano, drums, guitar, bass, keyboards, etc.).

### 4.2 Event Typologies and Composition Rules

When an agency manager schedules an event, the system checks whether selected musicians satisfy the composition constraints:

| Event Typology | Allowed Singers | Allowed Instrumentalists | Description |
| :--- | :--- | :--- | :--- |
| **Concerto** | At least 1 singer | At least 2 instrumentalists | Complex musical concert requiring full ensemble. |
| **Esibizione** | Exactly 1 singer | At least 1 instrumentalist | Vocal showcase accompanied by instruments. |
| **Strumentale** | Exactly 0 singers | At least 1 instrumentalist | Pure instrumental performance without vocalists. |

Additional invariants enforced:
- Event dates must strictly be scheduled for future dates (`date > today`).
- Venue descriptions must not exceed 120 characters.
- At least one eligible musician must be selected.
- All selected musicians must be chosen from the verified available pool for that specific target date.

### 4.3 Booking and Summons Lifecycle

1. **Availability Registration:** Musicians declare dates they are willing to perform. Availability is registered per single calendar day in the `disponibility` relation.
2. **Querying Available Roster:** The manager queries a specific date. The system filters musicians whose availability overlaps and returns the roster with their individual cachets.
3. **Drafting and Summons:** The manager selects participants and confirms event creation.
   - Initial event status: `IN_ATTESA` (pending confirmations).
   - The counter `MusicistiIncerti` is set to the total number of summoned artists.
   - Summons are recorded in `partecipazioni` with an initial outcome of `IN_ATTESA`.
4. **Musician Response:** Musicians review their pending summons:
   - **Accept (`ACCETTATO`):** Decrements `MusicistiIncerti`. If `MusicistiIncerti == 0`, the event status automatically transitions to `CONFERMATO`.
   - **Reject (`RIFIUTATO`):** Records rejection; the event remains unconfirmed.

### 4.4 Automated Monthly Financial Reporting

On the first day of each calendar month, the system evaluates the preceding month:
- **Event Audit Report:** Generates a list of all confirmed events, the performance date, location, event typology, and total expenditure.
- **Artist Compensation Statement:** Queries all participations in confirmed events during the month, groups by musician, multiplies total performances by individual cachets, and outputs the net liquidation fee.

---

## 5. Database Architecture

The persistence layer is backed by MySQL (InnoDB engine) with foreign keys and cascade rules to ensure relational integrity.

```
+--------------------+        +--------------------+
|     musicisti      |<-------|   disponibility    |
+--------------------+        +--------------------+
| PK IdMusicista     |        | PK,FK IdMusicista  |
|    Nome, Cognome   |        | PK    Data         |
|    Email, Password |        +--------------------+
|    Specializzazione|
|    Cachet, Telefono|        +--------------------+
+--------------------+<-------| strumentimusicisti |
  ^          ^                +--------------------+
  |          |                | PK,FK IdMusicista  |
  |          |                | PK    Strumento    |
  |          |                +--------------------+
  |          |
  |          +----------------+
  |                           |
  |     +------------------+  |     +------------------+
  |     | generemusicale-  |  |     |      eventi      |
  |     | cantanti         |  |     +------------------+
  |     +------------------+  |     | PK IdEvento      |
  |     | PK,FK IdMusicista|  |     |    Data, Luogo   |
  |     |    GenereMusicale|  |     |    Tipologia     |
  |     |    RegistroVocale|  |     |    CostoTotale   |
  |     +------------------+  |     |    MusicistiIn-  |
  |                           |     |    certi, Stato  |
  |                           |     +------------------+
  |                           |               ^
  |                           |               |
  |              +--------------------+       |
  +--------------|   partecipazioni   |-------+
                 +--------------------+
                 | PK,FK IdEvento     |
                 | PK,FK IdMusicista  |
                 |       esito        |
                 +--------------------+
```

### Relational Schema Summary

- `musicisti`: Base table containing core profile details, role, telephone, and performance cachet.
- `disponibility`: Composite primary key `(IdMusicista, Data)` referencing `musicisti` with cascade deletion.
- `strumentimusicisti`: Multi-valued attribute table capturing instrument proficiencies.
- `generemusicalecantanti`: Specialization table for singers detailing vocal genre and classical voice classification.
- `eventi`: Event schedule recording venue, date, typology, financial total, unresolved convocation counter, and overall status (`IN_ATTESA`, `CONFERMATO`).
- `partecipazioni`: Junction table binding musicians to events with composite key `(IdEvento, IdMusicista)` and booking outcome (`IN_ATTESA`, `ACCETTATO`, `RIFIUTATO`).

---

## 6. Project Structure

```
artist-management-system/
├── pom.xml                                      # Apache Maven project configuration
├── .gitignore                                   # Standard Git ignore configuration
├── README.md                                    # Comprehensive system documentation
│
├── ANAL_CD.vpp                                  # Visual Paradigm: Analysis Class Diagram
├── ANAL_UC_CDBCE_DS.vpp                         # Visual Paradigm: Use Cases and BCE Diagrams
├── PROG_CD_PD_SD_creaEvento_...vpp              # Visual Paradigm: Design Class & Sequence Diagrams
├── PROG_SD_INSERISCIDISPONIBILITA.vpp           # Visual Paradigm: Sequence Diagrams for Availability
│
├── db/
│   ├── README.md                                # Database installation and setup guide
│   └── gestioneeventi.sql                       # MySQL relational schema and initial seed data
│
└── src/
    ├── main/
    │   └── java/
    │       ├── boundary/                        # User interface layer (Java Swing)
    │       │   ├── IMain.java                   # Primary application entry point
    │       │   ├── IGestore.java                # Agency Manager dashboard
    │       │   ├── GUILoginMusicista.java       # Musician login portal
    │       │   ├── GUICreaEvento.java           # Event creation workflow
    │       │   ├── GUIDisponibilita.java        # Availability calendar manager
    │       │   ├── GUIConvocazioni.java         # Booking convocation dashboard
    │       │   ├── GUIMusicisti.java            # Musician directory browser
    │       │   ├── GUIMusicistiSelezionati.java # Selected roster review
    │       │   ├── GUIEventoCreato.java         # Post-creation summary view
    │       │   ├── GUIGestisciConvocazione.java # Individual convocation response
    │       │   ├── PopUpEsitoGestisci...java    # Confirmation feedback popup
    │       │   ├── ITempo.java                  # Time-simulation testing interface
    │       │   └── Login.java                   # Credential verification handler
    │       │
    │       ├── control/                         # Business logic controller layer
    │       │   └── GestioneEventi.java          # Singleton coordinator for system workflows
    │       │
    │       ├── database/                        # Persistence and Data Access Object layer
    │       │   ├── DBManager.java               # JDBC connection provider
    │       │   ├── EventoDAO.java               # Event transactional operations
    │       │   └── MusicistaDAO.java            # Musician transactional operations
    │       │
    │       ├── entity/                          # Domain model entities
    │       │   ├── Musicista.java               # Base musician model
    │       │   ├── Cantante.java                # Singer specialization
    │       │   ├── Strumentista.java            # Instrumentalist specialization
    │       │   ├── Evento.java                  # Base event model
    │       │   ├── Concerto.java                # Concert event specialization
    │       │   ├── Esibizione.java              # Exhibition event specialization
    │       │   └── Strumentale.java             # Instrumental event specialization
    │       │
    │       └── exception/                       # Custom domain exception classes
    │           ├── ControllerException.java     # Business rule violations
    │           ├── DatabaseException.java       # Persistence failures
    │           └── LoginException.java          # Authentication failures
    │
    └── test/
        └── java/
            └── test/                            # Automated test suites
                ├── CreaEventoTest.java          # Event creation validation tests
                ├── GestisciConvocazioniTest.java# Summons lifecycle & confirmation tests
                └── InserisciDisponibilitaTest.java # Availability submission tests
```

---

## 7. UML and Engineering Artifacts

The system was fully modeled using Visual Paradigm. All source model archives are included in the repository root:

- **`ANAL_UC_CDBCE_DS.vpp`:** Contains the primary Use Case diagrams, actors (Gestore, Musicista, Sistema/Tempo), and BCE (Boundary-Control-Entity) analysis representations.
- **`ANAL_CD.vpp`:** Analysis-level Class Diagram illustrating entity relationships, cardinality, and domain abstractions.
- **`PROG_CD_PD_SD_creaEvento_gestisciConvocazione_inviaReport.vpp`:** Detailed design diagrams including Design Class Diagrams (DCD), Package Diagrams (PD), and Sequence Diagrams (SD) for the three core scenarios: Event Creation, Summons Management, and Report Generation.
- **`PROG_SD_INSERISCIDISPONIBILITA.vpp`:** Detailed Sequence Diagrams for the Musician Availability submission workflow.

---

## 8. Testing and Quality Assurance

Testing was planned and executed using both black-box and white-box techniques:

### 8.1 Functional Testing (Black-Box)

Functional tests were designed applying Equivalence Partitioning (EP) and Boundary Value Analysis (BVA):
- **Equivalence classes for date formats:** valid standard (`yyyy-MM-dd`), invalid format strings, past dates, same-day dates.
- **Equivalence classes for event typologies:** matching valid uppercase/lowercase strings, unmapped arbitrary strings.
- **Ensemble constraint edge cases:** missing singers, insufficient instrumentalists, excess vocalists in instrumental-only events.
- **Availability date ranges:** start date after end date, start date before current timestamp, consecutive multi-day spans.

### 8.2 Structural Testing (White-Box)

Structural testing was conducted targeting 100% decision and branch coverage for critical control flows:
- **Cyclomatic Complexity calculation:** Flow graphs were derived for methods such as `creaEvento`, `getMusicistiDisponibili`, and `inserisciDisponibilita` to calculate independent execution paths ($V(G) = e - n + 2p$).
- Dedicated JUnit test cases verify exception paths (`expected = ControllerException.class`) whenever boundary thresholds are violated.

---

## 9. Getting Started

### 9.1 Prerequisites

- **Java Development Kit (JDK):** Version 21 or higher.
- **Apache Maven:** Version 3.8+ (for building and dependency resolution).
- **MySQL Database Server:** Version 8.0+ or MariaDB 10.4+ (e.g., via XAMPP, Homebrew, or Docker).

### 9.2 Database Setup

1. Start your local MySQL server on default port `3306`.
2. Access MySQL via command-line or administrative tool (e.g., phpMyAdmin, MySQL Workbench):
   ```sql
   CREATE DATABASE gestioneEventi;
   ```
3. Import the schema and initial seed data:
   ```bash
   mysql -u root -p gestioneEventi < db/gestioneeventi.sql
   ```
   *(By default, the connection is configured for host `localhost:3306`, user `root`, and empty password as defined in `DBManager.java`).*

### 9.3 Building with Maven

Compile the complete application and verify dependencies:

```bash
mvn clean compile
```

### 9.4 Running the Application

Launch the desktop application via Maven:

```bash
mvn exec:java
```

Alternatively, run the compiled classes directly:

```bash
java -cp target/classes:$(mvn dependency:build-classpath | grep -v '\[INFO\]') boundary.IMain
```

The primary launcher window (`IMain`) will appear, providing navigation between:
- **Login Musicista:** Access musician dashboard to manage availability and respond to convocations.
- **Accesso Gestore:** Access agency administrator panel to browse musicians, create events, and review bookings.
- **Simula Tempo:** Simulation tool to trigger monthly financial reporting mechanisms.

### 9.5 Running the Automated Tests

To execute the automated JUnit test suite against the local MySQL database:

```bash
mvn test
```

To run tests without requiring a live database connection during standard package packaging:

```bash
mvn package -DskipTests
```

---

## 10. Academic Context and Credits

This project was conceived and developed as the final course project for the **Software Engineering (Ingegneria del Software - 10 ECTS)** curriculum at the **University of Naples Federico II (Università degli Studi di Napoli Federico II)**, Department of Electrical Engineering and Information Technology (DIETI), Academic Year 2023/2024.

- **Development Team:** Gruppo DAM
  - Vincenzo Picone
  - Gianluca Vaino
  - Alberto Schianchi
- **Course Focus:** Requirements Analysis, Unified Modeling Language (UML), BCE Architecture, Relational Database Normalization, Software Testing (BVA, Structural Coverage), and Java Enterprise Design Patterns.
