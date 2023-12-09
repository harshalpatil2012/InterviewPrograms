# Key Components
Trade Module: Manages trade submissions, updates, cancellations, and reports.
Share Module: Handles share-related operations, including quantity management.
Wallet Module: Manages wallet-related functionalities, such as balance retrieval and updates.
API Gateway: Routes requests to appropriate microservices.
Monitoring Service: Collects system metrics for monitoring.

# Services:

1. Trade Submission:

Submit trades using the TradeService. Validate trades for quantity, balance, etc.

2. Trade Update:
Send trade updates through the TradeService. Validate and apply updates to existing trades.

3. Trade Cancellation:
Request trade cancellations via the TradeService. Trades are canceled, and statuses are updated.

4.Trade Reporting:
Use the ReportingServiceApplication to retrieve trade reports based on trader ID and keyword.

5. API Gateway
The ApiGatewayApplication acts as the API gateway, routing requests to microservices.

6. Monitoring
The MonitoringServiceApplication collects system metrics, providing insights into system performance.