function studentFormValidator() {
    let studentId = document.getElementById("studentId").value;
    let studentEmail = document.getElementById("studentEmail").value;
    let studentContact = document.getElementById("studentContact").value;
    let studentSession = document.getElementById("studentSession").value;
    let password = document.getElementById("studentPassword").value;
    let confirmPassword = document.getElementById("studentConfirmPassword").value;

    // id validation
    if (!studentId.startsWith("IT")) {
        document.getElementById("idMessage").innerHTML = "**invalid Student Id format...";
        console.log("**invalid contact format...")
        return false;
    }

    // email validation
    if (!studentEmail.startsWith("it") && !studentEmail.endsWith("@mbstu.ac.bd")) {
        document.getElementById("emailMessage").innerHTML = "**invalid email format...";
        console.log("**invalid contact format...")
        return false;
    }

    // contact validation
    let reg = /^(\+)?(88)?01[0-9]{9}$/;
    if (!reg.exec(studentContact)) {
        document.getElementById("contactMessage").innerHTML = "**invalid contact format...";
        console.log("**invalid contact format...")
        return false;
    }

    // session validation
    if (!studentSession.startsWith("20")) {
        document.getElementById("sessionMessage").innerHTML = "**invalid session format...";
        console.log("**invalid contact format...")
        return false;
    }

    // password validation
    if (password === "") {
        document.getElementById("passwordMessage").innerHTML = "**please fill password...";
        return false;
    }
    if (password.length < 5) {
        document.getElementById("passwordMessage").innerHTML = "**password length must be " +
            "greater then 5 character...";
        return false;
    }

    if (password.length > 20) {
        document.getElementById("passwordMessage").innerHTML = "**password length must be " +
            "smaller then 20 character...";
        return false;
    }

    if (password !== confirmPassword) {
        document.getElementById("passwordMessage").innerHTML = "**Do not match your password...";
        return false;
    }
}



function librarianFormValidator() {
    let librarianContact = document.getElementById("librarianContact").value;
    let librarianPassword = document.getElementById("librarianPassword").value;
    let librarianConfirmPassword = document.getElementById("librarianConfirmPassword").value;

    // contact validation
    let reg = /^(\+)?(88)?01[0-9]{9}$/;
    if (!reg.exec(librarianContact)) {
        document.getElementById("contactMessage").innerHTML = "**invalid contact format...";
        console.log("**invalid contact format...")
        return false;
    }

    // password validation
    if (librarianPassword === "") {
        document.getElementById("passwordMessage").innerHTML = "**please fill password...";
        return false;
    }
    if (librarianPassword.length < 5) {
        document.getElementById("passwordMessage").innerHTML = "**password length must be " +
            "greater then 5 character...";
        return false;
    }

    if (librarianPassword.length > 20) {
        document.getElementById("passwordMessage").innerHTML = "**password length must be " +
            "smaller then 20 character...";
        return false;
    }

    if (librarianPassword !== librarianConfirmPassword) {
        document.getElementById("passwordMessage").innerHTML = "**Do not match your password...";
        return false;
    }
}