document.addEventListener('DOMContentLoaded', function() {
    const signUpForm = document.querySelector('#signup-form');

    signUpForm.addEventListener('submit', function(event) {
        event.preventDefault();

        const name = document.querySelector('#name').value;
        const username = document.querySelector('#username').value;
        const email = document.querySelector('#email').value;
        const password = document.querySelector('#password').value;

        const formData = {
            name: name,
            username: username,
            email: email,
            password: password
        };

        fetch('/api/v1/auth/signup', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('Received JWT token:', data.token);
                window.location.href = '/sign-in';
            })
            .catch(error => {
                console.error('Error during sign-up:', error);
                // Handle error scenarios
            });
    });
});
