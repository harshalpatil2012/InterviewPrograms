# Trade System Overview

## Key Components

1. **Trade Module:** Manages trade submissions, updates, cancellations, and reports.
2. **Share Module:** Handles share-related operations, including quantity management.
3. **Wallet Module:** Manages wallet-related functionalities, such as balance retrieval and updates.
4. **API Gateway:** Routes requests to appropriate microservices.
5. **Monitoring Service:** Collects system metrics for monitoring.

## Services:

1. **Trade Submission:**
    - Submit trades using the TradeService.
    - Validate trades for quantity, balance, etc.

2. **Trade Update:**
    - Send trade updates through the TradeService.
    - Validate and apply updates to existing trades.

3. **Trade Cancellation:**
    - Request trade cancellations via the TradeService.
    - Trades are canceled, and statuses are updated.

4. **Trade Reporting:**
    - Use the ReportingServiceApplication to retrieve trade reports based on trader ID and keyword.

5. **Monitoring:**
    - The MonitoringServiceApplication collects system metrics, providing insights into system performance.

## Components:

1. **Trade Service:**
    - Main entry point for all trade-related operations.
    - Accepts requests from users, validates them, and interacts with other components to execute the trades.

2. **Trade Command Interface:**
    - Defines the common behavior for all trade operations.
    - Specific implementations like TradeBookingService and TradeCancellationService handle different commands.

3. **Trade Repository:**
    - Stores and manages data related to trades, including trade ID, share name, quantity, price, buy/sell flag, trader ID, and status.

4. **Wallet Service:**
    - Manages user wallets, including getting the current balance and updating it after a trade.

5. **Share Service:**
    - Manages share information, including getting the available quantity and updating it after a trade.

6. **Trade Exchange Client:**
    - Interacts with the external share exchange to send trade updates and update share quantities.

7. **Validation Service:**
    - Validates trade requests before processing them.

8. **API Gateway:**
    - Additionally, we can use SpringCloud Gateway. This will route requests to respective microservices.

## Data Flow:

1. User submits a trade request (buy or sell) to the Trade Service.
2. Trade Service validates the request using the Validation Service.
3. If the request is valid, the Trade Service delegates the execution to the appropriate Trade Command implementation (e.g., TradeBookingService for buy orders).
4. The Trade Command interacts with other services as needed:
    - Wallet Service to update user balance.
    - Share Service to update share quantity.
    - Trade Exchange Client to send trade updates to the exchange.
    - Trade Repository to persist the trade data.
5. The Trade Service updates the trade status based on the execution outcome.

## External Communication:

- The Trade Exchange Client communicates with the external share exchange using a specific protocol (e.g., REST API, message queue).
- The Trade Service might also expose APIs for other services to interact with it.

## Additional Design Considerations:

- **Transaction Management:**
    - To ensure data consistency across different services, transactions should be used to wrap updates to multiple resources.

- **Error Handling:**
    - The system should gracefully handle and log errors during trade execution.

- **Monitoring and Logging:**
    - Monitoring key metrics like trade volume and user activity is crucial for analyzing system performance and identifying potential issues.

- **Scalability and Performance:**
    - The system is designed to handle high volumes of trade requests and ensure smooth operation with increasing load.

## Technologies:

- **Spring Boot:**
    - Provides a framework for building microservices with features like dependency injection, configuration management, and web development support.

- **Spring Data JPA:**
    - Provides access to the database using a familiar JPA interface.

- **Prometheus:**
    - Enables monitoring and collecting metrics for system performance analysis.

- **Database Choice:**
    - The system utilizes a relational database (e.g., PostgreSQL, MySQL) to persist trade and user-related data.

- **Other Libraries:**
    - Depending on specific implementation choices, additional libraries for queueing, messaging, and communication might be used.