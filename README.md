# Amigo Market

App Android (Kotlin + Jetpack Compose) de compra/venta entre estudiantes.

## Estructura de paquetes

```
co.edu.amigo.market/
├── MainActivity.kt                         (Integrador 2)
├── data/
│   ├── model/Models.kt                     (Integrador 1)
│   ├── Repository.kt                       (Integrador 1)
│   └── SeedData.kt                         (E2)
├── viewmodel/                              (B2, C1, D1)
└── ui/
    ├── theme/                              (E1)
    ├── navigation/Routes.kt, AppNavGraph.kt (Integradores)
    ├── components/                         (E4, A1, A2, B1, C2)
    └── screens/                            (resto)
```

## Flujo de trabajo

- `main` está protegida: todo cambio entra por Pull Request con al menos 1 aprobación.
- Crea una rama por tarea: `feature/<codigo>-<descripcion>` (ej. `feature/E2-seed-data`).
