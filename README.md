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

# Todo

* Column code completion

# References

* PRQL Home: https://prql-lang.org/
* PRQL Grammar: https://github.com/PRQL/prql/blob/main/prql-lezer/src/prql.grammar#L32
* PRQL extension for Visual Studio Code: https://marketplace.visualstudio.com/items?itemName=PRQL-lang.prql-vscode