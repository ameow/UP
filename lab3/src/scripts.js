
var uniqueId = function() {
	var date = Date.now();
	var random = Math.random() * Math.random();

	return Math.floor(date * random).toString();
};

var theMessage = function(theText, theName, theEdit, theDelete) {
	return {
		description:theText,
		user:theName,
		theEdit: !!theEdit,
		theDelete: !!theDelete,
        id: uniqueId()
	};
};

var messageList = [];

function run() {
    var appContainer = document.getElementsByClassName('main')[0];
 
 
    appContainer.addEventListener('click', delegateEvent);

    var allMessages = restore() || [];


    createAllMessages(allMessages);


}

function createAllMessages(allMessages) {
	for(var i = 0; i < allMessages.length; i++){
		addTodo(allMessages[i]);
		}
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
    var isDeleteM = event.target.parentNode.parentNode.getAttribute('isDelete');
    if (usernameM != name) {
        alert("You are not allowed to edit messages of " + usernameM);
        return;
    }
    if(isDeleteM == 'false'){
        var textM = event.target.parentNode.nextSibling.nextSibling.innerHTML;
        var _message = prompt("Edit text message: ", textM);
        if (_message != null) {
            event.target.parentNode.nextSibling.nextSibling.innerHTML = _message + ' (Edited)';
            event.target.parentNode.previousSibling.previousSibling.setAttribute('checked', 'checked');
            swapEditAttribute(event.target.parentNode.parentNode.getAttribute('message-id'), _message);
        } else{
            alert('Error!');
        }
        } else{
        alert('Error! Message is deleted!!!');
    }
}

function swapEditAttribute(valueId, newText) {
    for (var i = 0; i < messageList.length; i++) {
        if (messageList[i].id == valueId) {
            messageList[i].theEdit = true;
            messageList[i].description = newText + ' (Edited)';
            store(messageList);
            return;
        }
    }
}
 
 
function deleteTextMessage(event){
    var usernameM = event.target.parentNode.parentNode.firstChild.innerHTML;
    var isDeleteM = event.target.parentNode.parentNode.getAttribute('isDelete');
    if (usernameM != name) {
        alert("You are not allowed to remove messages of " + usernameM);
        return;
    }
    if(isDeleteM == 'false') {
        var isDeleteAnswer = confirm("Do you really want to delete a message?");
        if(isDeleteAnswer) {
            event.target.parentNode.nextSibling.innerHTML = 'Message is deleted';
            event.target.parentNode.parentNode.setAttribute('isDelete', 'true');
            swapDeleteAttribute(event.target.parentNode.parentNode.getAttribute('message-id'));
        }
    } else{
        alert('Error!');
    }
}

function swapDeleteAttribute(valueId) {
    for (var i = 0; i < messageList.length; i++) {
        if (messageList[i].id == valueId) {
            messageList[i].theDelete = true;
            messageList[i].description = 'Message is deleted';
            store(messageList);
            return;
        }
    }
}

 
function onAddButtonClick() {
    var todoText = getMessage();

    if (!todoText) {
        return;
        }

    var newMessage = theMessage(todoText, name, false, false);
    addTodo(newMessage);
    todoText = '';
    document.getElementById("Write-message").value = '';

    store(messageList);
}
 
 
function addTodo(newMessage) {
    var item = createMessage(newMessage);
    var items = document.getElementsByClassName('Read-message')[0];
    messageList.push(newMessage);
    items.appendChild(item);
    items.scrollTop +=items.scrollHeight;

}


function createMessage(newMessage) {
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
    if (newMessage.theEdit) {
        checkMessage.setAttribute('checked', 'checked');
    }
    username.appendChild(document.createTextNode(newMessage.user));
    textMessage.appendChild(document.createTextNode(newMessage.description));
 
    button_img_edit.appendChild(img_edit);
    button_img_delete.appendChild(img_delete);

    message.setAttribute('message-id', newMessage.id);
    message.setAttribute('isDelete', newMessage.theDelete);
    message.appendChild(username);
    message.appendChild(checkMessage);
    message.appendChild(document.createTextNode(newMessage.user));
    message.appendChild(button_img_edit);
    message.appendChild(button_img_delete);
    message.appendChild(textMessage);
    return message;
}

function store(listToSave) {

    //alert(JSON.stringify(listToSave, null, 2));
	if(typeof(Storage) == "undefined") {
		alert('localStorage is not accessible');
		return;
	}

	localStorage.setItem("CHATs messageList", JSON.stringify(listToSave));
}

function restore() {
	if(typeof(Storage) == "undefined") {
		alert('localStorage is not accessible');
		return;
	}

	var item = localStorage.getItem("CHATs messageList");

	return item && JSON.parse(item);
}

