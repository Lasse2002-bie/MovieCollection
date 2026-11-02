# FURPS – vores filmsamling

|   | Krav                                        | Opfyldt? | Hvor         |
|---|---------------------------------------------|----------|--------------|
| F | Man kan søge på en del af en titel          | Ja       | US4, `domainmodel.MovieCollection.searchMovies` |
| U | Der vises en besked, hvis filmlisten er tom | Ja       | `ui.UserInterface.showAllMovies` |
| R | Man skal bekræfte med "ja", før en film slettes| Ja       | `ui.UserInterface.deleteMovie` |_
| P | Søgning går listen igennem én gang | Ja | `domainmodel.MovieCollection.searchMovies` |
| S | ui.UserInterface taler kun med domainmodel.Controller, aldrig direkte med domainmodel.MovieCollection| Ja | `UserInterface` (feltet `controller`)|
