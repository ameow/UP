
function run() {
    var appContainer = document.getElementsByClassName('main')[0];
 
 
    appContainer.addEventListener('click', delegateEvent);
 
}
 
function delegateEvent(evtObj) {
    if (evtObj.type === 'click' && evtObj.target.classList.contains('Right_Block_Button_Enter')) {
        onAddButtonClick(evtObj);
    }
    if (evtObj.type === 'click' && evtObj.target.classList.contains('Left_Block_Buttons_Edit_Name')) {
        renameFunction(evtObj);
    }
    if (evtObj.type === 'click' && evtObj.target.classList.contains('edit_pic_button')) {
        editTextMessage(evtObj);
    }
    if (evtObj.type === 'click' && evtObj.target.classList.contains('delete_pic_button')) {
        deleteTextMessage(evtObj);
    }
 
}
 
function EnterNameFunction(){
    var _name = document.getElementById("Enter_name").value;
    if ((_name != '') && (_name != null)){
        name = _name;
        location.href='Test.html';
    } else{
        alert('Enter your name!');
    }
}
 
 
function renameFunction(){
    var _name = prompt("Enter your name: ", name);
    if(_name != '') {
        name = _name;
    }
}
 
function getMessage() {
    message = document.getElementById("Write-message").value;
    return message;
}
 
 
 
function editTextMessage(event){
    var usernameM = event.target.parentNode.parentNode.firstChild.innerHTML;
    if (usernameM != name) {
        alert("You are not allowed to edit messages of " + usernameM);
    return;
    }
 
    if(event.target.parentNode.nextSibling.nextSibling.innerHTML != 'Message is deleted'){
        var textM = event.target.parentNode.nextSibling.nextSibling.innerHTML;
        var _message = prompt("Edit text message: ", textM);
        var usernameM = event.target.parentNode.previousSibling.previousSibling.previousSibling.innerHTML;
        if ((_message != null) && (name == usernameM)) {
            event.target.parentNode.nextSibling.nextSibling.innerHTML = _message + ' (Edited)';
            event.target.parentNode.previousSibling.previousSibling.setAttribute('checked', 'checked');
        } else{
            alert('Error!');
        }
        } else{
        alert('Error! Message is deleted!!!');
    }
 
}
 
 
function deleteTextMessage(event){
    var usernameM = event.target.parentNode.parentNode.firstChild.innerHTML;
    if (usernameM != name) {
        alert("You are not allowed to remove messages of " + usernameM);
    return;
    }
    var textM = event.target.parentNode.nextSibling.innerHTML;
    var usernameM = event.target.parentNode.previousSibling.previousSibling.previousSibling.previousSibling.innerHTML;
    if((textM != 'Message is deleted') && (usernameM == name)) {
        var isDelete = confirm("Do you really want to delete a message?");
        if(isDelete) {
            event.target.parentNode.nextSibling.innerHTML = 'Message is deleted';
        }
    } else{
        alert('Error!');
    }
}
 
function onAddButtonClick() {
    var todoText = getMessage();
    addTodo(todoText);
    todoText = '';
    document.getElementById("Write-message").value = '';
}
 
 
function addTodo(value) {
    if (!value) {
    return;
    }
 
    var item = createMessage(value);
    var items = document.getElementsByClassName('Read-message')[0];
    items.appendChild(item);
    items.scrollTop +=items.scrollHeight;
}
 
function createItem(text) {
    var divItem = document.createElement('div');
    divItem.classList.add('message');
 
    divItem.appendChild(createMessage(text));
 
 
    return divItem;
}
 
 
function createMessage(text) {
    var username = document.createElement('div');
    var message = document.createElement('div');
    var checkMessage = document.createElement('input');
    var textMessage = document.createElement('div');
    var button_img_edit = document.createElement('button');
    var button_img_delete = document.createElement('button');
    var img_edit = document.createElement('img');
    var img_delete = document.createElement('img');
 
    img_edit.classList.add('edit_pic_button');
    img_delete.classList.add('delete_pic_button');
    img_edit.setAttribute('src', 'edit.png');
    img_delete.setAttribute('src', 'delete.png');
    message.classList.add('message');
    username.classList.add('username_message');
    checkMessage.classList.add('check_message');
    textMessage.classList.add('text_message');
    button_img_edit.classList.add('edit_message_button');
    button_img_delete.classList.add('delete_message_button');
    checkMessage.setAttribute('type', 'checkbox');
    checkMessage.setAttribute('disabled', 'disabled');
    username.appendChild(document.createTextNode(name));
    textMessage.appendChild(document.createTextNode(text));
 
    button_img_edit.appendChild(img_edit);
    button_img_delete.appendChild(img_delete);
 
    message.appendChild(username);
    message.appendChild(checkMessage);
    message.appendChild(document.createTextNode(name));
    message.appendChild(button_img_edit);
    message.appendChild(button_img_delete);
    message.appendChild(textMessage);
    return message;
}