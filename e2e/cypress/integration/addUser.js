context('add user', () => {
    it('register', () => {
        cy.registerUser();
    })

    it('login', () => {
        cy.loginUser();
    });
});
