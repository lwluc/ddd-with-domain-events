# Architekturentscheidungsprotokoll (ADR)

Dieses Verzeichnis enthält alle **Architecture Decision Records (ADRs)** für das Projekt **DDD with Domain Events**.

---

## 📋 Was ist ein ADR?

Ein **Architecture Decision Record (ADR)** ist ein leichtgewichtiges Dokument, das eine wichtige architektonische Entscheidung festhält:

- **Kontext**: Warum wurde die Entscheidung getroffen?
- **Entscheidung**: Was wurde beschlossen?
- **Konsequenzen**: Welche Auswirkungen hat die Entscheidung?
- **Alternativen**: Welche anderen Optionen wurden in Betracht gezogen?

ADRs helfen dabei:
- ✅ Entscheidungen zu dokumentieren und nachzuvollziehen
- ✅ Wissen im Team zu teilen
- ✅ Konsistenz in der Architektur zu wahren
- ✅ Neue Teammitglieder einzuarbeiten

---

## 📚 ADR-Format

Alle ADRs folgen diesem Format (basierend auf [MADR](https://adr.github.io/madr/)):

```markdown
# ADR {NNNN}: {Titel}

## Status
✅ Akzeptiert / ❌ Abgelehnt / 🔄 In Diskussion

## Kontext
{Warum wurde die Entscheidung nötig?}

## Entscheidung
{Was wurde beschlossen?}

## Konsequenzen
{Positive, negative und neutrale Auswirkungen}

## Alternativen
{Andere Optionen, die in Betracht gezogen wurden}

## Referenzen
{Externe Links und Ressourcen}
```

---

## 📝 Aktuelle ADRs

| Nr. | Titel | Status | Datum | Autor |
|-----|-------|--------|------|-------|
| [0001](./0001-adresse-als-value-object.md) | Adresse als Value Object statt Entity modellieren | ✅ Akzeptiert | 2025-05-29 | Vibe Code |
| [0002](./0002-optional-fuer-nullable-felder.md) | Optional<T> für optionale Felder in Domain Events | ✅ Akzeptiert | 2025-05-29 | Vibe Code |

---

## 🔧 Wie neue ADRs erstellen?

1. **Nummer vergeben**: Nächste freie Nummer verwenden (z.B. 0003)
2. **Datei erstellen**: `docs/adr/{NNNN}-{titel-in-kebab-case}.md`
3. **Template verwenden**: Siehe oben oder kopiere eine bestehende ADR
4. **PR erstellen**: ADR als Teil eines Pull Requests einreichen
5. **Review**: Team-Mitglieder reviewen die Entscheidung
6. **Akzeptieren**: Nach Konsens Status auf ✅ setzen

---

## 📖 Nützliche Ressourcen

- [MADR – Markdown Architecture Decision Records](https://adr.github.io/madr/)
- [ADR GitHub Template](https://github.com/adr/adr-tools)
- [Architecture Decision Records – Martin Fowler](https://martinfowler.com/articles/architecture-decision-record.html)
- [ADR Tools](https://github.com/npryce/adr-tools)

---

## 🎯 Best Practices für ADRs

### ✅ Do's
- **Kurz und präzise** – Maximal 1-2 Seiten
- **Fokussiert** – Eine Entscheidung pro ADR
- **Kontext erklären** – Warum war die Entscheidung nötig?
- **Alternativen nennen** – Welche Optionen wurden abgelehnt?
- **Konsequenzen aufzeigen** – Was sind die Auswirkungen?
- **Aktualisieren** – ADRs bei Änderungen anpassen

### ❌ Don'ts
- **Zu lang** – Keine Romane schreiben
- **Zu vage** – Klare Entscheidungen treffen
- **Zu technisch** – Für alle Team-Mitglieder verständlich
- **Vergessen** – ADRs nach Entscheidungen erstellen
- **Ignorieren** – ADRs bei der Entwicklung beachten

---

## 🔍 ADR-Suche

| Thema | ADR |
|-------|-----|
| Value Objects | [0001](./0001-adresse-als-value-object.md) |
| Null-Safety | [0002](./0002-optional-fuer-nullable-felder.md) |
| Domain Events | [0002](./0002-optional-fuer-nullable-felder.md) |

---

*Letzte Aktualisierung: 2025-05-29*
