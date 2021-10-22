$(document).ready(function(){
    console.log('Готов!');
    restartAllUser();
    // alert("The text has been changed.");
    $('.AddBtn').on('click', function (event) {
        console.log('user');
        let user = {
            id: $("#id").val(),
            name: $("#name").val(),
            lastName: $("#lastName").val(),
            age: $("#age").val(),
            email: $("#email").val(),
            password: $("#password").val(),
            roles: getRole("#formControlSelect")

        }
        console.log(user);
        fetch("admin/new", {
            method: "POST",
            headers: {
                "Content-Type": "application/json;charset=utf-8"
            },
            body: JSON.stringify(user)
        }).then(() => openTabById('UsersTable'))
            .then(() => restartAllUser());

    });
});

function restartAllUser() {
    let UserTableBody = $("#user_table_body")

    UserTableBody.children().remove();

    fetch("/usersRest")
        .then((response) => {
            response.json().then(data => data.forEach(function (item, i, data) {
                let TableRow = createTableRow(item);
                UserTableBody.append(TableRow);

            }));
        }).catch(error => {
        console.log(error);
    });
}

function createTableRow(u) {
    let userRole = "";
    for (let i = 0; i < u.roles.length; i++) {
        userRole += " " + u.roles[i].role;
    }
    return `<tr id="user_table_row">
            <td>${u.id}</td>
            <td>${u.name}</td>
            <td>${u.lastName}</td>
            <td>${u.age}</td>
            <td>${u.email}</td>
            <td>${userRole}</td>
            <td>
            <a  href="/${u.id}" class="btn btn-info eBtn" >Edit</a>
            </td>
             <td>
            <a  href="/admin/users/${u.id}" class="btn btn-danger delBtn">Delete</a>
            </td>
        </tr>`;
}

function openTabById(tab) {
    $('.nav-tabs a[href="#' + tab + '"]').tab('show');
}

function getRole(address) {
    let data = [];
    $(address).find("option:selected").each(function () {

      data.push({id: $(this).val(), role: $(this).attr("name"), authority: $(this).attr("name")})
    });
    return data;
}


document.addEventListener('click', function (event) {
    event.preventDefault()
    if ($(event.target).hasClass('delBtn')) {
        let href = $(event.target).attr("href");
        delModalButton(href)
    }


    if ($(event.target).hasClass('eBtn')) {
        let href = $(event.target).attr("href");
        $(".editUser #exampleModal").modal();
        // $(".editUser #modalEdit").modal();

        $.get(href, function (user) {
            $(".editUser #id").val(user.id);
            $(".editUser #firstNameEd").val(user.name);
            $(".editUser #lastNameEd").val(user.lastName);
            $(".editUser #ageEd").val(user.age);
            $(".editUser #emailEd").val(user.email);

            $(".editUser #passwordEd").val(user.password);
            $(".editUser #selectRoleEd").val(user.roles);
        });
    }

    if ($(event.target).hasClass('editButton')) {
        let user = {
            id:$('#id').val(),
            name:$('#firstNameEd').val(),
            lastName:$('#lastNameEd').val(),
            age:$('#ageEd').val(),
            email:$('#emailEd').val(),

            password:$('#passwordEd').val(),
            roles: getRole("#selectRoleEd")

        }
        editModalButton(user)
        console.log(user);
    }

    if ($(event.target).hasClass('logout')) {
        logout();
    }
    if ($(event.target).hasClass('btnUserTable')) {
        userTable();
    }

});

function delModalButton(href) {
    fetch(href, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        }
    }).then(() => restartAllUser());
}
function editModalButton(user) {
    fetch("/users", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        },
        body: JSON.stringify(user)
    }).then(function (response) {
        $('input').val('');
        $('.editUser #exampleModal').modal('hide');
        restartAllUser();
    })
}

function logout(){
    document.location.replace("/logout");
}
function userTable(){
      document.location.replace("/user/lk");

}