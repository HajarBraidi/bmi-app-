#  BmiApp — Android Labs 4, 5 & 6

> Projet réalisé dans le cadre du module **M4.5.1 Mobile Dev**  
> 2A D2S — ENSIAS 2026

---

## Structure du projet

| Activité | Description |
|---|---|
| `HubActivity` | Écran d'accueil — navigation vers toutes les activités |
| `BmiActivity` | Calcul de l'IMC (poids / taille) |
| `CurrencyConverterActivity` | Convertisseur de devises (MAD, EUR, USD) |
| `AboutActivity` | Page de présentation personnelle |
| `VitalActivity` | Saisie et sauvegarde des signes vitaux |
| `RecordsActivity` | Affichage des enregistrements en tableau |
---

##  Fonctionnalités

### Lab 4 — BMI App
- Saisie du poids (kg) et de la taille (cm)
- Calcul de l'IMC avec catégorie OMS
  - `< 18.5` → Underweight
  - `18.5 – 24.9` → Normal
  - `25.0 – 29.9` → Overweight
  - `≥ 30.0` → Obese
- Validation des champs avec messages d'erreur inline
- Feedback visuel : couleur change selon la catégorie

### Lab 5 — Navigation Hub
- `HubActivity` comme launcher principal
- Navigation par **Intents explicites** via boutons
- **Options Menu** dans la toolbar pour la même navigation
- `AboutActivity` avec retour automatique au Hub

### Lab 6 — Vital Signs Recorder
- Saisie de : date, heure, systolique, diastolique,
  fréquence cardiaque, température, glycémie
- **DatePicker** et **TimePicker** Material
- Validation des plages médicales :

| Champ | Plage valide |
|---|---|
| Systolique | 80 – 200 mmHg |
| Diastolique | 50 – 130 mmHg |
| Fréquence cardiaque | 40 – 200 bpm |
| Température | 34 – 42 °C |
| Glycémie | 50 – 400 mg/dL |

- Sauvegarde locale en **SQLite** (`vitals.db`)
- Partage du record via **ACTION_SEND** (Email, WhatsApp…)
- Affichage de tous les records dans un **TableLayout** scrollable

---

##  Technologies utilisées

- **Kotlin**
- **Android SDK** — min API 24
- **ViewBinding**
- **Material Components** (TextInputLayout, DatePicker, TimePicker)
- **SQLite** — base de données locale
- **Explicit Intents** — navigation entre activités

---

## Installation

1. Clonez le dépôt :
```bash
