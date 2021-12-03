describe("As a logged out user", () => {
  it("I can submit feedback about the service via the feedback form", () => {
    cy.visit("/");
    cy.get("a").contains("feedback").click();
    cy.get('[type="radio"]').first().check();
    cy.get("textarea").type("Nothing, it's amazing.");
    cy.get("form").submit();
  });
});
