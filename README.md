prql-jetbrains-plugin
========================

<!-- Plugin description -->
PRQL plugin allows you to edit [PRQL](https://prql-lang.org/) in JetBrains IDEs.

Features:

- Syntax parsing
- Code highlight
- Code format
- SQL injection
- Code completion
- SQL transpile support
- SQL Target dialect support from `SQL dialects`
- Execute PRQL by JDBC Console
- Sync transpiled SQL from `@PRQL` to `@Query`

<!-- Plugin description end -->

# Installation

- Using IDE built-in plugin system:

  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "prql"</kbd> >
  <kbd>Install Plugin</kbd>

- Manually:

  Download the [latest release](https://github.com/linux-china/prql-jetbrains-plugin/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

# Type System

* primitives: int/float(underscore allowed), bool(true/false). 
* string: `"hello world"`, `'hello'`, `"""I said "hello world"!"""`,  
* date and time: `@2022-12-31`, ` @2020-01-01T13:19:55-08:00`, `@16:54:32`
* range: `50..100`
* array: `{}`
* tuple: `()`
* function: `let add a b -> a+b`
            
### String

* F-strings: Build up a new string from a set of columns or values
* S-strings: Insert SQL statements directly into the query. Use when PRQL doesn’t have an equivalent facility.

# Todo

* Column code completion

# References

* PRQL Home: https://prql-lang.org/
* PRQL Grammar: https://github.com/PRQL/prql/blob/main/prql-lezer/src/prql.grammar#L32
* PRQL extension for Visual Studio Code: https://marketplace.visualstudio.com/items?itemName=PRQL-lang.prql-vscode