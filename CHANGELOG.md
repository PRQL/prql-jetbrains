<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# prql-jetbrains-plugin Changelog

## [Unreleased]

### Added

* Target dialect support from `Settings` -> `Languages & Frameworks` -> `SQL dialects`

## [0.5.0]

### Added

* `let` support for table definition
* Syntax high light for S-Strings and F-Strings
* Column high light for S-Strings and F-Strings
* Add S-Strings for Join
* `duckdb` added in dialects

## [0.4.2]

### Added

* `from_text` support

## [0.4.1]

### Fixed

* Remove DumbAware to avoid index not ready exception

## [0.4.0]

### Added

* switch enhancement
* append support
* Excluding columns: `select ![title, composer]`
* Numbers can now contain underscores: `select [ small = 1.000_000_1, big = 5_000_000]`
* Use `prqlc` instead of `prql-compiler` command line tool: please install it by `cargo install --bins --features=cli prql-compiler`

### Changed

* Plugin version now follows PRQL version

## [0.2.1]

### Fixed

* Some bugs fixed

## [0.2.0]

### Added

* PRQL Injection for `@PRQL` annotation in Java
* Icon support for completion items
* SQL transpile by `prql-comiler` command line
* Table name completion if datasource setup in IDE

### Fixed

* `sort -age` marked as error now

## [0.1.0]

### Added

- Syntax support
- SQL injection
- Code completion
