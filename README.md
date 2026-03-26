# Earthquake Monitoring & Notification System

An event-driven Java simulation designed to process real-time seismic data and manage user notification subscriptions based on geographic proximity.

## Key Impact
- **Event-Driven Architecture:** Engineered a simulation engine that merges disparate data streams (user updates and seismic events) into a synchronized chronological timeline.
- **Custom Data Structures:** Developed internal implementations of Doubly Linked Lists and FIFO Queues to optimize memory management and handle a rolling 6-hour history window.
- **Spatial Alert Logic:** Implemented magnitude-weighted Euclidean distance thresholds to ensure only high-risk users receive targeted notifications.

## Technologies
- **Language:** Java
- **Concepts:** Object-Oriented Programming (OOP), Data Structures, Event-Driven Design
- **Tools:** Git

## Features
- **Real-time Processing:** Handles 'add', 'delete', and 'query' commands for watchers alongside earthquake events.
- **Automated History Cleanup:** Automatically purges records older than 6 hours to maintain system efficiency.
- **Threshold Calculation:** Uses the formula $2 \cdot \text{magnitude}^3$ to determine the notification radius for every seismic event.

## How to Run
1. Compile all files in the `src` directory.
2. Run `Main.java`.
3. Provide the paths for your watcher and earthquake data files when prompted.
