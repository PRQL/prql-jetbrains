prql-jetbrains-plugin
========================

<!-- Plugin description -->
PRQL plugin allows you to edit [PRQL](https://prql-lang.org/) in JetBrains IDEs.

Features:

- Syntax parsing
- Code highlight
- SQL injection
- Code completion
- SQL transpile support by `prqlc` command line: install it by `cargo install prqlc`

<!-- Plugin description end -->

# Installation

- Using IDE built-in plugin system:

  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "prql"</kbd> >
  <kbd>Install Plugin</kbd>

- Manually:

  Download the [latest release](https://github.com/linux-china/prql-jetbrains-plugin/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

# Todo

* Run PRQL: https://intellij-support.jetbrains.com/hc/en-us/community/posts/360006491899-Run-sql-statement-programatically

# References

* PRQL Home: https://prql-lang.org/
* PRQL Grammar: https://github.com/PRQL/prql/blob/main/prql-lezer/src/prql.grammar#L32
* Syntax and Parsing Notes: https://github.com/chris-pikul/go-prql/blob/main/SYNTAX-NOTES.md