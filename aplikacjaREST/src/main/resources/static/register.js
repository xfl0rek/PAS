document.getElementById('registerForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const formData = new FormData(this);
    const data = Object.fromEntries(formData);

    fetch('/client/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(data => {
            console.log('Registration successful:', data);
            alert('Registration successful!');
        })
        .catch(error => {
            console.error('Registration failed:', error);
            alert('Registration failed.');
        });
});
