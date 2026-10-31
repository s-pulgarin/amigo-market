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

## Ramas

| Rama | Uso |
|---|---|
| `main` | Versiones entregables (`v1.0`, ...). Protegida. |
| `develop` | Integración del equipo (rama por defecto). Protegida. |
| `feature/negocios` | Equipo Negocios |
| `feature/usuarios` | Equipo Usuarios |
| `feature/reservas` | Equipo Reservas |
| `feature/compras` | Equipo Compras |
| `feature/base` | Equipo Base |

## Cómo hacer un Pull Request

### 1. Clonar el repo (solo la primera vez)

```bash
git clone https://github.com/s-pulgarin/amigo-market.git
cd amigo-market
```

Ábrelo en Android Studio con **File → Open**.

### 2. Cambiarte a la rama de tu equipo

```bash
git fetch origin
git checkout feature/<tu-equipo>      # ej. git checkout feature/compras
```

### 3. Antes de empezar a programar, trae lo último

```bash
git pull                              # lo último de tu rama
git merge origin/develop              # lo que ya se integró en develop
```

### 4. Programa y haz commits pequeños

```bash
git add .
git commit -m "B2: agregar y quitar productos del carrito"
```

Usa como prefijo el código de tu tarea (B2, C1, D1, E2...).

### 5. Verifica que compila **antes** de subir

En Android Studio: **Build → Make Project**, o en la terminal:

```bash
./gradlew assembleDebug               # Windows: gradlew assembleDebug
```

No subas código que no compila: bloquea a todo el equipo.

### 6. Sube tus cambios

```bash
git pull                              # por si un compañero subió algo
git push
```

### 7. Abre el Pull Request en GitHub

1. Entra a https://github.com/s-pulgarin/amigo-market
2. Aparece un aviso amarillo **"feature/... had recent pushes"** → clic en **Compare & pull request**.
   (Si no aparece: pestaña **Pull requests → New pull request**.)
3. Verifica: **base: `develop`** ← **compare: `feature/<tu-equipo>`**.
4. Título: `[Equipo] Qué hiciste` (ej. `[Compras] Carrito y checkout`).
5. Llena la plantilla de la descripción y pide revisión a un compañero de **otro** equipo (Reviewers).
6. Clic en **Create pull request**.

### 8. Revisión y merge

- Se necesita **1 aprobación** y resolver todos los comentarios.
- Si te piden cambios: corrígelos en tu misma rama, haz `commit` y `push`; el PR se actualiza solo.
- Cuando esté aprobado, clic en **Squash and merge**. **No borres** la rama del equipo: se sigue usando.

### Reglas del contrato

- **No cambies firmas** de funciones, modelos, rutas ni ViewModels (`Models.kt`, `Repository.kt`, `Routes.kt`, stubs). Si necesitas cambiar una, avísale al Integrador primero.
- Solo modifica los archivos que le corresponden a tu equipo.
- Nunca hagas `push` directo a `main` ni a `develop` (está bloqueado).

### Si hay conflictos

Al hacer `git merge origin/develop` o `git pull` puede salir `CONFLICT`. Abre el archivo en Android Studio (**Git → Resolve Conflicts**), elige qué conservar, y luego:

```bash
git add .
git commit
git push
```

Si no estás seguro, pide ayuda antes de borrar código de otro.
