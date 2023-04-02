<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# prql-jetbrains-plugin Changelog

## [Unreleased]

## [0.7.0]

### Added

- Updated to PRQL 0.7.0

## [0.6.1]

### Added

- Inspection: sync transpiled SQL from PRQL to `@Query`
- Updated to PRQL 0.6.1

## [0.6.0]

### Added

- case instead of switch
- loop support
- some bugs fixed
- prql-lib bundled: `prqlc` command line tool is not required anymore

## [0.5.3]

### Added

- Introduce prql-java library to call PRQL API instead of using `prqlc` command line, and no need to install `prqlc` anymore.
- Code format support
- Brace matcher support

## [0.5.2]

### Added

- Execution icon: execute PRQL by JDBC Console. Please set up local datasource and open an active JDBC Console first.

## [0.5.1]

### Added

- Target dialect support from `Settings` -> `Languages & Frameworks` -> `SQL dialects`

### Fixed

- table name completion with multi schemas, for example `Postgres`.

## [0.5.0]

### Added

- `let` support for table definition
- Syntax high light for S-Strings and F-Strings
- Column high light for S-Strings and F-Strings
- Add S-Strings for Join
- `duckdb` added in dialects

## [0.4.2]

### Added

- `from_text` support

## [0.4.1]

### Fixed

- Remove DumbAware to avoid index not ready exception

## [0.4.0]

### Added

- switch enhancement
- append support
- Excluding columns: `select ![title, composer]`
- Numbers can now contain underscores: `select [ small = 1.000_000_1, big = 5_000_000]`
- Use `prqlc` instead of `prql-compiler` command line tool: please install it by `cargo install --bins --features=cli prql-compiler`

### Changed

- Plugin version now follows PRQL version

## [0.2.1]

### Fixed

- Some bugs fixed

## [0.2.0]

### Added

- PRQL Injection for `@PRQL` annotation in Java
- Icon support for completion items
- SQL transpile by `prql-comiler` command line
- Table name completion if datasource setup in IDE

### Fixed

- `sort -age` marked as error now

## [0.1.0]

### Added

- Syntax support
- SQL injection
- Code completion
