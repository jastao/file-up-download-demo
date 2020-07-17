import React, { useState } from 'react';
import FileService from './FileService.js';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';

export default function UploadFile(props) {

  const[error, setError] = useState('');
  const[message, setMessage] = useState('');
  const[file, setFile] = useState({});

  const uploadFile = (event) => {

    //prevent onChange event from happening
    event.preventDefault();
  
    if(!file) {
      setError('Please upload a file.');
      return;
    }

    if(file.size >= 10000000) {
      setError('File size cannot exceeds limit of 10MB');
      return;
    }

    // create the data to send out
    const data = new FormData();
    console.log(file);
    data.append('file', file);
    data.append('filename', file ? file.name : '');

    // call the rest endpt to upload file
    FileService.postUploadFile(data)
        .then(data => {
          if(data.status === 200 || data.status === 201) {
            setMessage("Upload completed successfully");
            setError("")

            // refresh the list of files
            props.updateFilesList();
          } else if (data.status === 400) {
            setMessage("")
            setError("A file with the same filename already existed.")
          }
        })
        .catch(error => {
          setMessage("")
          setError(error.message)
        })
      

  }
  
  const onFileChange = (event) => {
  
    // simply save the file locally
    console.log("Saving file locally: " + event.target.files[0].name)
    setMessage("");
    setError("");
    setFile(event.target.files[0]);
  };

  return(
    <React.Fragment>
      <div style={{ marginBottom: 10}}>
        <span style={{fontWeight: 'bold'}}>Note: File size cannot exceeds 10MB</span>
      </div>
      <div className="container">
        {error && <header style={{color: 'red'}}>{error}</header>}
        {message && <header style={{color: 'green'}}>{message}</header>}
      </div>
      <TextField
        name="upload"
        type="file"
        onChange={onFileChange} 
      />
      <Button variant="outlined" color="primary" onClick={uploadFile}>Upload</Button>
    </React.Fragment>
  )
}