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

BookingController (/bookings)
Access: Manager or Waiter (@PreAuthorize("hasRole('MANAGER') or hasRole('WAITER')"))

| Method | Endpoint         | Description                | Request Body      | Response             |
| ------ | ---------------- | -------------------------- | ----------------- | -------------------- |
| POST   | `/bookings`      | Create a new booking       | `Booking` JSON    | `BookingDTO` JSON    |
| GET    | `/bookings`      | Get all bookings           | None              | List of `BookingDTO` |
| GET    | `/bookings/{id}` | Get a booking by ID        | None              | `BookingDTO`         |
| PUT    | `/bookings/{id}` | Update a booking           | `Booking` JSON    | Updated `BookingDTO` |
| PATCH  | `/bookings/{id}` | Partially update a booking | `BookingDTO` JSON | Updated `BookingDTO` |
| DELETE | `/bookings/{id}` | Delete a booking           | None              | `204 No Content`     |


CustomerController (/customers)
Access:
CUSTOMER for create, update, patch, delete.
MANAGER for viewing all customers.
CUSTOMER or MANAGER for getting customer by ID.

| Method | Endpoint          | Description                  | Request Body       | Access Role         | Response                   |
| ------ | ----------------- | ---------------------------- | ------------------ | ------------------- | -------------------------- |
| POST   | `/customers`      | Create a new customer        | `Customer` JSON    | CUSTOMER            | `CustomerDTO` JSON         |
| GET    | `/customers`      | Get all customers            | None               | MANAGER             | List of `CustomerDTO`      |
| GET    | `/customers/{id}` | Get a customer by ID         | None               | CUSTOMER or MANAGER | `Customer` JSON            |
| PUT    | `/customers/{id}` | Update a customer completely | `Customer` JSON    | CUSTOMER            | Updated `CustomerDTO` JSON |
| PATCH  | `/customers/{id}` | Partially update a customer  | `CustomerDTO` JSON | CUSTOMER            | Updated `CustomerDTO` JSON |
| DELETE | `/customers/{id}` | Delete a customer            | None               | CUSTOMER            | `204 No Content`           |


MenuItemController (/menu-items)
Access:
All endpoints restricted to MANAGER.
| Method | Endpoint           | Description                   | Request Body       | Access Role | Response                   |
| ------ | ------------------ | ----------------------------- | ------------------ | ----------- | -------------------------- |
| POST   | `/menu-items`      | Create a new menu item        | `MenuItem` JSON    | MANAGER     | `MenuItemDTO` JSON         |
| GET    | `/menu-items`      | Get all menu items            | None               | MANAGER     | List of `MenuItemDTO`      |
| GET    | `/menu-items/{id}` | Get menu item by ID           | None               | MANAGER     | `MenuItemDTO` JSON         |
| PUT    | `/menu-items/{id}` | Update a menu item completely | `MenuItem` JSON    | MANAGER     | Updated `MenuItemDTO` JSON |
| PATCH  | `/menu-items/{id}` | Partially update a menu item  | `MenuItemDTO` JSON | MANAGER     | Updated `MenuItemDTO` JSON |
| DELETE | `/menu-items/{id}` | Delete a menu item            | None               | MANAGER     | `204 No Content`           |


OrderController (/orders)
Access:
Endpoints accessible to WAITER and KITCHEN roles.

Controlled via @PreAuthorize("hasRole('WAITER') or hasRole('KITCHEN')").
| Method | Endpoint       | Description                | Request Body    | Access Role    | Response                |
| ------ | -------------- | -------------------------- | --------------- | -------------- | ----------------------- |
| POST   | `/orders`      | Place a new order          | `Order` JSON    | WAITER/KITCHEN | `OrderDTO` JSON         |
| GET    | `/orders`      | Get all orders             | None            | WAITER/KITCHEN | List of `OrderDTO`      |
| GET    | `/orders/{id}` | Get order by ID            | None            | WAITER/KITCHEN | `OrderDTO` JSON         |
| PUT    | `/orders/{id}` | Update an order completely | `Order` JSON    | WAITER/KITCHEN | Updated `OrderDTO` JSON |
| PATCH  | `/orders/{id}` | Partially update an order  | `OrderDTO` JSON | WAITER/KITCHEN | Updated `OrderDTO` JSON |
| DELETE | `/orders/{id}` | Delete an order            | None            | WAITER/KITCHEN | `204 No Content`        |


RestaurantTableController (/tables)
Access:
Endpoints accessible only to ADMIN role.
Controlled via @PreAuthorize("hasRole('ADMIN')").

| Method | Endpoint       | Description                   | Request Body              | Access Role | Response                          |
| ------ | -------------- | ----------------------------- | ------------------------- | ----------- | --------------------------------- |
| POST   | `/tables`      | Create a new restaurant table | `RestaurantTable` JSON    | ADMIN       | `RestaurantTableDTO` JSON         |
| GET    | `/tables`      | Get all tables                | None                      | ADMIN       | List of `RestaurantTableDTO`      |
| GET    | `/tables/{id}` | Get table by ID               | None                      | ADMIN       | `RestaurantTableDTO` JSON         |
| PUT    | `/tables/{id}` | Update a table completely     | `RestaurantTable` JSON    | ADMIN       | Updated `RestaurantTableDTO` JSON |
| PATCH  | `/tables/{id}` | Partially update a table      | `RestaurantTableDTO` JSON | ADMIN       | Updated `RestaurantTableDTO` JSON |
| DELETE | `/tables/{id}` | Delete a table                | None                      | ADMIN       | `204 No Content`                  |

UserController (/users)
Access:

Endpoints accessible only to ADMIN role.

Controlled via @PreAuthorize("hasRole('ADMIN')").

| Method | Endpoint      | Description              | Request Body | Access Role | Response               |
| ------ | ------------- | ------------------------ | ------------ | ----------- | ---------------------- |
| POST   | `/users`      | Create a new user        | `User` JSON  | ADMIN       | `UserDTO` JSON         |
| GET    | `/users`      | Get all users            | None         | ADMIN       | List of `UserDTO`      |
| GET    | `/users/{id}` | Get user by ID           | None         | ADMIN       | `UserDTO` JSON         |
| PUT    | `/users/{id}` | Update a user completely | `User` JSON  | ADMIN       | Updated `UserDTO` JSON |
| PATCH  | `/users/{id}` | Partially update a user  | `User` JSON  | ADMIN       | Updated `UserDTO` JSON |
| DELETE | `/users/{id}` | Delete a user            | None         | ADMIN       | `204 No Content`       |


