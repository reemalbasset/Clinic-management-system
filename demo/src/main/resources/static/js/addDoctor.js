const registrationForm = document.getElementById('registrationForm');
const nameInput = document.getElementById('name');
const emailInput = document.getElementById('email');
const passwordInput = document.getElementById('password');
const specialtyInput = document.getElementById('specialty');
const universityNameInput = document.getElementById('universityName');

registrationForm.addEventListener('submit', function(event) {
    if (!isFormValid()) {
        event.preventDefault();
    }
});

function isFormValid() {
    let isValid = true;

    if (!validateName(nameInput.value)) {
        isValid = false;
    }

    if (!validateEmail(emailInput.value)) {
        isValid = false;
    }

    if (!validatePassword(passwordInput.value)) {
        isValid = false;
    }

    if (!validateSpecialty(specialtyInput.value)) {
        isValid = false;
    }

    if (!validateUniversityName(universityNameInput.value)) {
        isValid = false;
    }

    return isValid;
}

function validateName(name) {
    const nameRegex = /^[A-Za-z ]+$/;
    const nameErrorMessage = document.getElementById('nameErrorMessage');
    if (!nameRegex.test(name)) {
        nameErrorMessage.textContent = 'Name must contain only alphabetical characters.';
        return false;
    } else {
        nameErrorMessage.textContent = '';
        return true;
    }
}

function validateEmail(email) {
    const emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
    const emailErrorMessage = document.getElementById('emailErrorMessage');
    if (!emailRegex.test(email)) {
        emailErrorMessage.textContent = 'Invalid email address.';
        return false;
    } else {
        emailErrorMessage.textContent = '';
        return true;
    }
}

function validatePassword(password) {
    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
    const passwordErrorMessage = document.getElementById('passwordErrorMessage');
    if (!passwordRegex.test(password)) {
        passwordErrorMessage.textContent = 'Password must be at least 8 characters long and contain at least one letter and one digit.';
        return false;
    } else {
        passwordErrorMessage.textContent = '';
        return true;
    }
}

function validateSpecialty(specialty) {
    return true;
}

function validateUniversityName(universityName) {
    return true;
}
