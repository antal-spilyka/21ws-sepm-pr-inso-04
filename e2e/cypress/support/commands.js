function checkLogin(cy) {
    cy.contains('Welcome').should('be.visible');
    cy.get('button[name="logout"]').click();
    cy.contains('a', 'Login').should('be.visible');
    cy.contains('a', 'Register').should('be.visible');
}

Cypress.Commands.add('loginUser', () => {
    cy.fixture('settings').then(settings => {
        cy.visit(settings.baseUrl);
        cy.contains('a', 'Login').click();
        cy.get('input[name="email"]').type(settings.registerUsers.user1.email);
        cy.get('input[name="password"]').type(settings.registerUsers.user1.pw);
        cy.contains('button', 'Login').click();
        checkLogin(cy);
    })
})
Cypress.Commands.add('registerUser', () => {
    cy.fixture('settings').then(settings => {
        cy.visit(settings.baseUrl + '/#' + settings.paths.register);
        cy.get('input[name="firstName"]').type(settings.registerUsers.user1.firstName);
        cy.get('input[name="lastName"]').type(settings.registerUsers.user1.lastName);
        cy.get('input[name="email"]').type(settings.registerUsers.user1.email);
        cy.get('input[name="phone"]').type(settings.registerUsers.user1.phone);
        cy.get('input[name="password"]').type(settings.registerUsers.user1.pw);
        cy.contains('button', 'Next').click();
        cy.get('mat-select[name="country"]').click();
        cy.get('mat-option').contains(settings.registerUsers.user1.country).click();
        cy.get('input[name="zip"]').type(settings.registerUsers.user1.zip);
        cy.get('input[name="city"]').type(settings.registerUsers.user1.city);
        cy.get('input[name="street"]').type(settings.registerUsers.user1.street);
        cy.contains('button', 'Sign Up').click();
        checkLogin(cy);
    })
})
