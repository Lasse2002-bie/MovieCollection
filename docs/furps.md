# FURPS – vores filmsamling

|   | Krav                                        | Opfyldt? | Hvor         |
|---|---------------------------------------------|----------|--------------|
| F | Man kan søge på en del af en titel          | Ja       | US4, `MovieCollection.searchMovies` |
| U | Der vises en besked, hvis filmlisten er tom | Ja       | `UserInterface.showAllMovies` |
| R | Man skal bekræfte med "ja", før en film slettes| Ja       | `UserInterface.deleteMovie` |_
| P | Søgning går listen igennem én gang | Ja | `MovieCollection.searchMovies` |
| S | UserInterface taler kun med Controller, aldrig direkte med MovieCollection| Ja | `UserInterface` (feltet `controller`)|
