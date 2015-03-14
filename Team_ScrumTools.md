# Abbildung auf die Tools #

## Issue Tracking ##

Zur Erfassung der User-Stories, der Tasks und der Fehler verwenden wir das Issue Tracking System von Google Code. Die Kategorisierung und Priorisierung erfolgt dabei über selbst definierte Labels.

### Kategorisierung ###

  * `Type-UserStory`: User-Story
  * `Type-Bug`: Fehler
  * `Type-Task`: Technische Aufgabe (Task)
  * `Type-Test`: Manueller Test

### Ablage ###

  * `PartOf-ProductBacklog`: User-Story im Product-Backlog
  * `PartOf-SprintBackog`: User-Story oder Fehler im Sprint-Backlog
  * `PartOf-OpenBugs`: Offene Fehler
  * `PartOf-OpenTasks`: Offene Tasks

### Komponenten ###
  * `Component-Data`: Datenhaltung
  * `Component-GUI`: Benutzeroberfläche / Visualierung
  * `Component-Conn`: (Bluetooth) Verbindung

### Priorisierung ###

  * `Priority-Critical`: Umsetzung ist sofort erforderlich, da das Team sonst nicht weiter arbeiten kann (sollte nicht auftreten)
  * `Priority-High`: Umsetzung innerhalb des nächsten Sprints gewünscht
  * `Priority-Low`: Umsetzung kann beliebig verschoben werden

**User Stories:**
  * `StoryPrio-Core`: Kernfunktionalität, die umgesetzt werden muss
  * `StoryPrio-Nice`: Nice To Have Features, die zwar wünschenswert aber nicht zwingend erforderlich sind
  * `StoryPrio-Vision`: Visionen bzw. Ideen für die Zukunft, können bei Gelegenheit umgesetzt werden

### Abhängigkeiten ###
Werden zu einer User Story Tasks angelegt, so können diese über das Label _Blocking_ verknüpft werden. Dazu schreibt man einfach die ID(s) der Task in das Label Blocking der User Story.

## Wiki ##

In dem Wiki von Google Code legen wir unsere Dokumentation ab. Dazu zählt die Projektdokumentation, Technische Dokumentation und die Bedierungsanleitung.

  * `Doc-Team`: Projekt oder Arbeitsweise
  * `Doc-Technical`:  Technische Dokumentation
  * `Doc-Manual`: Bedienungsanleitung