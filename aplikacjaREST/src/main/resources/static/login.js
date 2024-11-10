document.getElementById('loginForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const formData = new FormData(this);
    const data = Object.fromEntries(formData);

    fetch('/client/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Login failed');
            }
        })
        .then(data => {
            console.log('Login successful:', data);
            alert('Login successful!');
            window.location.href = '/home.html';
        })
        .catch(error => {
            console.error('Login failed:', error);
            alert('Login failed.');
        });
});
