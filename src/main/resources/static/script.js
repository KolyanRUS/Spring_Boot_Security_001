async function getResponse() {
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
getResponse()