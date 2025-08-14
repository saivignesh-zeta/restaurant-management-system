Features & User Roles
  Customer -	Book tables online
  Waiter - Take and send orders to the kitchen via tablets
  Kitchen -	View live orders, mark orders as prepared
  Manager -	Generate bills, record payments

BillController (/bills)
Access: Manager only (@PreAuthorize("hasRole('MANAGER')"))

| Method | Endpoint          | Description             | Request Body                  | Response                              |
| ------ | ----------------- | ----------------------- | ----------------------------- | ------------------------------------- |
| POST   | `/bills`          | Create a new bill       | `Bill` JSON                   | `BillDTO` JSON                        |
| GET    | `/bills`          | Get all bills           | None                          | List of `BillDTO`                     |
| GET    | `/bills/{id}`     | Get a bill by ID        | None                          | `BillDTO`                             |
| PUT    | `/bills/{id}`     | Update a bill           | `Bill` JSON                   | Updated `BillDTO`                     |
| PATCH  | `/bills/{id}`     | Partially update a bill | `BillDTO` JSON                | Updated `BillDTO`                     |
| POST   | `/bills/{id}/pay` | Pay a bill              | `paymentMethod` (query param) | Updated `BillDTO` with payment status |
| DELETE | `/bills/{id}`     | Delete a bill           | None                          | `204 No Content`                      |
