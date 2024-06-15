document.addEventListener('DOMContentLoaded', function () {
    const signInForm = document.getElementById('signin-form');

    signInForm.addEventListener('submit', function (event) {
        event.preventDefault(); // Prevent the form from submitting normally

        // Fetch form data
        const formData = new FormData(signInForm);
        const formDataObj = Object.fromEntries(formData.entries()); // Convert FormData to object

        // Send POST request to login endpoint
        fetch('/api/v1/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formDataObj),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Login failed.');
                }
                return response.json();
            })
            .then(data => {
                // Save JWT token to localStorage
                localStorage.setItem('token', data.token);

                // Fetch user data using the JWT token
                return fetch('/api/v1/auth/get-logged-user', {
                    headers: {
                        'Authorization': 'Bearer ' + data.token,
                    },
                });
            })
            .then(respons => {
                console.log(respons)
                if (!respons.ok) {
                    throw new Error('Failed to fetch user data.');
                }
                return respons.json();
            })
            .then(userData => {
                console.log(JSON.stringify(userData))
                // Save user data to localStorage
                localStorage.setItem('user', JSON.stringify(userData));

                // Redirect to feed page
                window.location.href = '/feed';
            })
            .catch(error => {
                console.error('Error logging in:', error);
                // Handle error: display message to user, reset form, etc.
            });
    });
});
