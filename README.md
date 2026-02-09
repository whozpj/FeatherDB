# FeatherDB

A lightweight, file-based relational database engine built from the ground up in Java. FeatherDB implements a custom SQL-like query language with a complete parser, query optimizer, and execution engine, using the file system for persistent data storage.

## Features

FeatherDB supports a comprehensive set of SQL-like operations:

| Command | Description |
|---------|-------------|
| **USE** | Switch between databases for subsequent operations |
| **CREATE** | Create new databases or tables with specified schemas |
| **INSERT** | Add records to tables with automatic primary key generation |
| **SELECT** | Query tables with WHERE conditions, JOIN operations, and result ordering |
| **UPDATE** | Modify existing records based on specified conditions |
| **ALTER** | Modify table structure by adding or dropping columns |
| **DELETE** | Remove records that match specified conditions |
| **DROP** | Remove tables or entire databases |
| **JOIN** | Perform inner joins on multiple tables with Cartesian product results |


### Key Constraints

- All primary keys are named `id` and auto-generated sequentially
- Database and table names are stored in lowercase but are case-sensitive for queries
- SQL keywords are reserved and case-insensitive
- Column names preserve user-specified case but are case-insensitive for matching
- Foreign key relationships are application-level (not enforced by the engine)
- Data must be valid according to column type definitions

## Requirements

- **Java Development Kit (JDK) 21 LTS** or later
- **Apache Maven 3.9.0** or later

## Getting Started

### Building the Project

```bash
# Clone or navigate to the project directory
cd FeatherDB

# Build the project
./mvnw clean install

# Compile only
./mvnw compile
```

### Running Tests

```bash
# Execute all unit tests
./mvnw test
```



## Project Structure

```
FeatherDB/
├── src/main/java/          # Core implementation
├── src/test/java/          # Unit tests
├── databases/              # Persistent data storage
├── BTREE_IMPLEMENTATION.md  # B-tree indexing documentation
├── pom.xml                 # Maven configuration
└── README.md              # This file
```

## Implementation Details

For detailed information about the B-tree indexing implementation, see [`BTREE_IMPLEMENTATION.md`](BTREE_IMPLEMENTATION.md).


---

**Author:** Prithviraj  
**Last Updated:** January 2026
