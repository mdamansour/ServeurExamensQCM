# 📘 ServeurExamensQCM

**ServeurExamensQCM** est une application Java complète dédiée à la **gestion** et à la **passation d'examens sous forme de Questionnaires à Choix Multiples (QCM)**.
Elle propose **deux interfaces distinctes** (professeurs et étudiants), avec une **correction automatique**, une **gestion des médias**, et un **suivi détaillé des résultats**.

---

## 🚀 Fonctionnalités Principales

### 👨‍🏫 Pour les Professeurs

* **Gestion des examens**

  * Création, modification et suppression des examens
  * Association à une **filière** et un **niveau**

* **Éditeur de questions**

  * Ajout de questions QCM
  * Possibilité de joindre des **fichiers médias** :

    * Images
    * Vidéos
    * Audio

* **Barème personnalisé**

  * Configuration des points pour :

    * Réponse juste
    * Réponse fausse
    * Question non répondue

* **Suivi des résultats**

  * Consultation des notes par examen
  * Exportation des résultats au format **CSV** (compatible Excel)

---

### 👨‍🎓 Pour les Étudiants

* **Tableau de bord**

  * Accès aux examens disponibles selon :

    * Filière
    * Niveau

* **Interface de passation**

  * Interface intuitive et ergonomique
  * Accès aux médias ajoutés par le professeur

* **Historique des notes**

  * Consultation des résultats des examens déjà passés

---

## 🔐 Système & Sécurité

* **Authentification sécurisée**

  * Connexion par **email** et **mot de passe**
  * Gestion distincte des rôles (Professeur / Étudiant)

* **Calcul automatisé**

  * Correction automatique des examens
  * Normalisation des notes sur **20 points**

---

## 🛠️ Stack Technique

* **Langage** : Java
* **Interface Graphique** : Java Swing

  * `JFrame`, `JPanel`, `JTable`, etc.
* **Base de données** : MySQL (via JDBC)
* **Architecture** : MVC (Modèle – Vue – Contrôleur)

---

## 📂 Structure du Projet

```
src/
├── modele/
│   ├── Etudiant.java
│   ├── Professeur.java
│   ├── Examen.java
│   ├── Question.java
│   └── Resultat.java
│
├── database/
│   ├── Connexion.java
│   └── DAO classes
│
└── gui/
    ├── AuthentificationGUI.java
    ├── TableauBordProfesseur.java
    ├── TableauBordEtudiant.java
    └── Interfaces diverses
```

---

## ⚙️ Configuration

### 🗄️ Base de données

* Importer le schéma SQL dans un serveur **MySQL**
* Nom de la base de données :

  ```
  examens_qcm_bd
  ```

### 🔑 Connexion

* Identifiants par défaut (configurés dans `Connexion.java`) :

  ```
  utilisateur : root
  mot de passe : (vide)
  ```

### ▶️ Lancement de l'application

* Exécuter la classe :

  ```
  AuthentificationGUI.java
  ```

---

## 👥 Auteurs

Ce projet a été réalisé par :

* **Mohammed Amansour**
* **Franklin Hamunyemba**

📚 *Projet universitaire — Année 2025/2026*
🎓 *Université Sidi Mohamed Ben Abdellah – Fès*
