context('add message', () => {
    let msgText = 'msg' + new Date().getTime();

    it('register', () => {
        cy.registerUser();
    })

    it('login', () => {
        cy.loginUser();
    });
});
