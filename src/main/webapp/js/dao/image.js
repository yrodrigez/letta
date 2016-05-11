

function arrayBufferToBase64( buffer ) {
    var binary = '';
    var bytes = new Uint8Array( buffer );
    var len = bytes.byteLength;
    for (var i = 0; i < len; i++) {
        binary += String.fromCharCode( bytes[ i ] );
    }
    return window.btoa( binary );
}

function handleFileSelect(evt) {
    var f = evt.target.files[0]; // FileList object

    // Only process image files.
    if (f.type.match('image.*')) {
        alert(f.type);

        var reader = new FileReader();

        // Read in the image file as a data URL.
        reader.onload = function(e) {
            alert(arrayBufferToBase64(reader.result));

            document.getElementById('img').innerHTML = arrayBufferToBase64(reader.result);
            var arr = f.split(".");
            document.getElementById('img_ext').innerHTML = arr[arr.length-1] ;
        };

        reader.readAsArrayBuffer(f);
    }
}



  