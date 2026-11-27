# Checkout Flow Analysis and Refactoring Opportunities

**Repository:** Groceries  
**Issue:** #12 — Document checkout flow and identify refactoring opportunities  
**Sprint:** 2  
**Status:** Analysis completed; refactoring proposals pending mentor review

## 1. Purpose

Analyze the existing checkout implementation, understand its responsibilities and dependencies, and identify improvements to maintainability, error handling, and data consistency.

No production behavior changes are included in this task.

## 2. Current checkout flow

The checkout process begins with the `POST /checkout` endpoint in `CheckoutController`.

1. `CheckoutController` receives a `CheckoutRequest` containing a basket of product names and an optional phone number.
2. The controller converts a null basket to an empty list and a null phone number to an empty string.
3. `CheckoutService.checkout()` creates a `Receipt` and uses `createBasketMap()` to count quantities of individual products.
4. For each unique product, `ProductDao` retrieves its price and `DiscountDao` checks for a discount.
5. `DiscountService.countDiscount()` calculates the amount payable for products with a discount definition.
6. The service calculates the total price and populates the receipt.
7. If the phone number belongs to an existing loyalty customer, `LoyaltyService` adds points based on the integer part of the total price.
8. `CheckoutEventPublisher` handles the completed checkout event. In the `local` profile, the event is logged; in environments with SQS enabled, it is published to Amazon SQS.
9. The service returns the receipt.

### Flow diagram

```text
POST /checkout
      |
      v
CheckoutController
      |
      v
CheckoutService
      |
      +-- createBasketMap()
      |
      +-- ProductDao --------> PostgreSQL
      |
      +-- DiscountDao -------> PostgreSQL
      |
      +-- DiscountService
      |     (calculate discounts)
      |
      +-- Receipt
      |
      +-- LoyaltyService ---> ClientDao ---> PostgreSQL
      |
      +-- CheckoutEventPublisher ---> Amazon SQS
      |
      v
Return Receipt
```

## 3. Identified issues and improvement opportunities

### 3.1 Unsafe Optional handling

`ProductDao.getPriceByName()` returns `Optional<Double>`, but `CheckoutService` retrieves its value using `.get()` without checking whether the product exists.

**Risk:** Missing products can cause `NoSuchElementException`.

**Proposal:** Retrieve prices once and use explicit missing-product handling, e.g. `orElseThrow()` with a meaningful exception. Agree on the expected HTTP response with the mentor.

### 3.2 Repeated DAO calls

`DiscountDao.getDiscountByProductName()` may be called twice for a single product when a discount is present.

**Risk:** Unnecessary database queries and less readable code.

**Proposal:** Fetch the discount once, store it in a local `Optional<Discount>`, and reuse the result.

### 3.3 Misleading variable names

The `countDiscounts` variable and `Receipt.totalDiscounts` represent the subtotal payable for discounted products rather than the actual amount saved.

**Risk:** Developers and API consumers may misunderstand the values.

**Proposal:** Rename local variables to better represent their purpose. Treat changes to JSON response fields as a separate API contract decision.

### 3.4 Monetary precision

Prices and totals currently use `double`.

**Risk:** Floating-point rounding inaccuracies in financial calculations.

**Proposal:** Evaluate migrating monetary values to `BigDecimal`, including entity mappings, receipt DTOs, discount calculations, database schema, and event payloads.

### 3.5 Database and SQS consistency

Loyalty points are updated before checkout event publication.

**Risk:** If event publishing fails, the customer may receive an error after points have already been saved.

**Proposal:** Review transaction boundaries and investigate reliable event-publishing strategies, such as the Transactional Outbox pattern.

### 3.6 Concurrent loyalty updates

`LoyaltyService` reads the current points balance and then writes the updated total.

**Risk:** Concurrent requests may overwrite each other's updates.

**Proposal:** Consider an atomic database increment or an appropriate locking strategy.

### 3.7 Discount calculation edge cases

`DiscountService.countDiscount()` calculates the `gratis` discount using floating-point division.

**Risk:** The formula may charge fractional quantities in scenarios where only complete items should be free.

**Proposal:** Confirm the intended business rule and add boundary tests before changing the formula.

## 4. Recommended scope for Issue #13

For the first refactoring, focus on a small and testable change:

- Eliminate repeated discount DAO calls.
- Retrieve each product price once.
- Improve local variable naming.
- Preserve existing pricing and discount behavior.
- Run existing `CheckoutServiceTest` tests and add focused tests if necessary.

Handle changes to monetary types, transaction design, SQS reliability, and API contracts in separate follow-up tasks.

## 5. Definition of Done

- [x] Analyze the checkout request flow.
- [x] Identify the main service dependencies.
- [x] Document database and external service interactions.
- [x] Identify at least two refactoring opportunities.
- [ ] Discuss proposed improvements with the mentor.
- [ ] Commit the analysis documentation and link it to Issue #12.