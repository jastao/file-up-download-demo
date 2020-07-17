import React from 'react';
import FileService from './FileService.js';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Button from '@material-ui/core/Button';
import Paper from '@material-ui/core/Paper';
import moment from 'moment';

const HTTP_HEADERS_CONTENT_DISPOSITION = 'Content-Disposition';

class FilesListing extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      files: null,
      hasFiles: false
    }

    this.refreshFilesListing = this.refreshFilesListing.bind(this);
    this.downloadFile = this.downloadFile.bind(this);
    this.convertFileSizeToMB = this.convertFileSizeToMB.bind(this);
  }

  componentDidMount() {
    this.refreshFilesListing();
  }

  refreshFilesListing() {
    FileService.getFilesListing()
      .then(response => response.json())
      .then(data => {
        console.log(data);
        if (data === null || data.length > 0) {
          this.setState({
            files: data,
            hasFiles: true
          })
        }
      })
      .catch(error => {
        this.setState({
          hasFiles: false
        })
      })
  }

  downloadFile(filename) {
    
    FileService.getDownloadFile(filename)
              .then(response => {
                const filename = response.headers.get(HTTP_HEADERS_CONTENT_DISPOSITION).split('filename=')[1].trim();

                response.blob().then(blobData => {
                  let url = window.URL.createObjectURL(blobData);
                  let a = document.createElement('a');
                  a.href = url;
                  a.download = filename;
                  a.click();
                });
              })
  }

  convertFileSizeToMB(size) {
    
    let bytesToKBFactor = 1000; 
    let bytesToMBFactor = 1000 * 1000;
    let convertSize = size;

    if( Math.floor(size / bytesToMBFactor) > 0) {
      return (size / bytesToMBFactor).toFixed() + ' MB';
    } else if ( Math.floor(size / bytesToKBFactor).toFixed() > 0) {
      return (size / bytesToKBFactor).toFixed() + ' KB';
    }
    return convertSize + ' Bytes';
  }

  render() {
    return (
      <div>
        <TableContainer component={Paper}>
          <Table size="small" aria-label="a dense table">
            <TableHead>
              <TableRow>
                <TableCell>Filename</TableCell>
                <TableCell>Upload Date</TableCell>
                <TableCell>Content Type</TableCell>
                <TableCell>File Size</TableCell>
                <TableCell>Download URL</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {this.state.hasFiles ? 
                (this.state.files.map((file, i) => (
                  <TableRow key={file.filename}>
                    <TableCell>{file.filename}</TableCell>
                    <TableCell>{moment(file.upload_date).format('YYYY-MM-DD MM:mm:SSS')}</TableCell>
                    <TableCell>{file.content_type}</TableCell>
                    <TableCell>{this.convertFileSizeToMB(file.file_size)}</TableCell>
                    <TableCell>
                      <Button variant="outlined" color="primary" onClick={() => this.downloadFile(file.filename)}>Download</Button>
                    </TableCell>
                  </TableRow>))
                  ) : (
                    <TableRow><TableCell>No upload file available.</TableCell></TableRow>
                  )
              }
            </TableBody>
          </Table>
        </TableContainer>
      </div>
    )
  }
}

export default FilesListing;