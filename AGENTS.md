---
name: ddd-with-domain-events
description: Referenzprojekt für DDD mit Domain Events
---

# DDD With Domain Events Demo

## Projekt Überblick

Das Referenzprojekt verdeutlicht, wie in DDD mit Domain Events Kommunikation in und zwischen Domänen fachlich ausdrucksstark abgebildet wird. Der Fokus soll ich hier ganz klar auf den Domain Events liegen und nicht auf sonstigem Infrastruktur-Code oder ähnlichem.

Zur Verdeutlichung arbeitet diese Projekt eine Beispiel aus einer Fachlichkeit die den meisten Nutzern geläufig ist, um die kognitive Last bereits durch die exemplarisch Fachlichkeit zu erhöhen.

## Architektur

- Clean Architecture: Domain (Model + Service) <- Use Case -> Adapter
  - Keine Infrastruktur in der Domäne, ausgenommen Dependency Injection Mechanismen
- Bausteine aus dem taktischen DDD:
  - Immutable Domänen Modelle (Value Object, Entity, Aggregate) dir Ihre Invariante abbilden, sich somit selbst validieren.
  - Domänen Events um den Zustand der Domäne zu verändern.
  - Domänen Services für Komplexe Operationen und übergreifende Validierungen.
- Domänen Events zur Kommunikation zwischen verschiedene Bounded Kontexten (ergibt auch sich aus den zuvor genannten Vorgaben) 

## Qualitätssicherung

- Kompilieren: `mvnw compile`
- Testen: `mvnw test`

## Dokumentation
- Dokumentiert wird in `/docs/architecture.adoc` mit PlantUML Diagrammen. Es wird das [arc42](https://arc42.org) format genutzt. Beschrieben werden nur die notwendigen Kapitel. 
- Dokumentation bauen: `mvnw -B org.asciidoctor:asciidoctor-maven-plugin:process-asciidoc`

## Vorgaben und Qualitätssicherung

- Commits klein und überschaubar halten.
- Jeder Commit muss kompilieren, die Tests müssen durchlaufen und die Dokumentation muss aktuell und konsistent sein.
- Die README.md muss aktuell gehalten werden.
