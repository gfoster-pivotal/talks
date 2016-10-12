var page = require('webpage').create();
var url = 'http://localhost:3000/books';
page.open(url, function (status) {
  console.log('page loaded: '+ status);
  if("success" == status){ 
	var bookBox = document.getElementsByClassName('commentBox');
  	console.log('exit happy');
	expect(bookBox).not.toBe(null);
  	console.log('exit happy');
  	phantom.exit(1);
  } else {
  	phantom.exit(0);
  }
return 1;
});
