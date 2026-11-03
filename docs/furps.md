# FURPS – vores filmsamling

|   | Krav                                                                                  | Opfyldt? | Hvor                                            |
|---|---------------------------------------------------------------------------------------|----------|-------------------------------------------------|
| F | Man kan søge på en del af en titel                                                    | Ja       | US4, `domainmodel.MovieCollection.searchMovies` |
| U | Der vises en besked, hvis filmlisten er tom                                           | Ja       | `ui.UserInterface.showAllMovies`                |
| R | Programmet må ikke crashe, hvis brugeren indtaster tekst, hvor der forventes et tal   | Ja       | `ui.UserInterface.ReadInt`                      |_
| P | En søgning skal kun gennemløbe filmsamlingen én gang                                  | Ja | `domainmodel.MovieCollection.searchMovies`      |
| S | UserInterface skal kun kommunikere med Controller og ikke direkte med MovieCollection | Ja | `ui.UserIntercace`                                |
