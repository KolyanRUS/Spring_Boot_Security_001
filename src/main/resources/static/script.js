async function add_user() {
    let email = document.getElementById('add_email').value;
    let login = document.getElementById('add_login').value;
    let password = document.getElementById('add_password').value;
    let role = document.getElementById('add_role').value;
    /*alert("email:1::"+email);
    setTimeout(() => { console.log("World!"); alert("world#1"); }, 10000);
    setTimeout(() => { console.log("World!"); alert("world#2"); }, 10000);
    setTimeout(() => { console.log("World!"); alert("world#3"); }, 10000);*/
    let response = await fetch('http://localhost:8080/api/addUser',{
        method: 'POST',
        body: JSON.stringify({
            name: login,
            email: email,
            roles: [
                {
                    "role": role,
                    "authority": role,
                    "userRoleId": getIdByRole(role)
                }
            ],
            password: password
        }),
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        }
    });
    await console.log('DELETE_USERS');
    let users = await fetch('http://localhost:8080/api/getUsers');
    let content = await users.json();
    let key;
    for(key in content) {
        let str = await document.getElementById('tr_id#'+content[key].id);
        await str.remove();
    }
    getUsers();
}
function getIdByRole(role) {
    if (role == "ROLE_ADMIN") {
        return 1;
    } else {
        return 2;
    }
}
async function getUsers() {
    let response = await fetch('http://localhost:8080/api/getUsers');
    let content = await response.json();
    await console.log('users:::');
    await console.log(content);
    await console.log(':::users');
    let list = await document.getElementById('elemGetUsers');
    let key;
    //list._clear();
    for(key in content) {
        let tr = document.createElement('tr');
        tr.id = `tr_id#${content[key].id}`;
        //tr.setAttribute("id","tr_id#${content[key].id}");
        tr.innerHTML = `
                        <td id="edit_id#${content[key].id}"><span>${content[key].id}</span></td>
                <td id="edit_role#${content[key].id}"><span>${content[key].role}</span></td>
                <td id="edit_name#${content[key].id}"><span>${content[key].name}</span></td>
                <td id="edit_password#${content[key].id}"><span>${content[key].password}</span></td>
                <td id="edit_email#${content[key].id}"><span>${content[key].email}</span></td>
                <td>
                                            <button type="button"
                                            onclick="form_edit(${content[key].id})"
                                            class="btn btn-info btn-sm" data-toggle="modal" data-target="#userId" value="${content[key].id}">${content[key].id}</button>
                </td>
                <td>
                                            <button type="button" onclick="delete_user(${content[key].id})" class="btn btn-info btn-sm">${content[key].id}</button>
                </td>
                                            `
        ;
        list.append(tr);
    }
}
async function form_edit(id/*,role,name,password,email*/) {//,${content[key].role},${content[key].name},${content[key].password},${content[key].email}
    await console.log('form_edit_BEGIN');
    let response = await fetch('http://localhost:8080/api/openModalById/'+id);
    let user = await response.json();
    await console.log(user);
    formEdit(user.id,user.role,user.name,user.password,user.email);
    await console.log('form_edit_END');
}
function formEdit(id,role,name,password,email) {
    document.getElementById('formEdit_id').value = id;
    document.getElementById('formEdit_role').value = role;
    document.getElementById('formEdit_name').value = name;
    document.getElementById('formEdit_password').value = password;
    document.getElementById('formEdit_email').value = email;
}
async function edit_user() {
    await console.log('EDIT_USER');
    let idd = await document.getElementById('formEdit_id').value;
    let email = await document.getElementById('formEdit_email').value;
    let name = await document.getElementById('formEdit_name').value;
    let password = await document.getElementById('formEdit_password').value;
    let role = await document.getElementById('formEdit_role').value;
    let response = await fetch('http://localhost:8080/api/editUser',{
        method: 'POST',
        body: JSON.stringify({
            id: idd,
            name: name,
            email: email,
            roles: [
                {
                    "role": role,
                    "authority": role,
                    "userRoleId": getIdByRole(role)
                }
            ],
            password: password
        }),
        headers: {
            "Content-Type": "application/json; charset=utf-8"
        }
    });
}
async function delete_user(id) {
    await console.log('DELETE_USER');
    let response = await fetch('http://localhost:8080/api/deleteById/'+id, {method: 'POST'});
    let str = await document.getElementById('tr_id#'+id);
    await str.remove();
}
getUsers()
/*async function getResponse() {
    let response = await fetch('http://localhost:8080/api/getUsers');
    let content = await response.json();
    await console.log(content);
    let list = await document.getElementById('elem');
    let key;
    for(key in content) {
        let tr = document.createElement('tr');
        tr.innerHTML = `<td>${content[key].id}</td><td>${content[key].name}</td><td>${content[key].password}</td><td>${content[key].email}</td><td>${content[key].role}</td>`;
        list.append(tr);
    }
}
getResponse()*/