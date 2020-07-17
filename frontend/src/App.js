import React from 'react';
import UploadFile from './UploadFile.js';
import FilesListing from './FilesListing.js';
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import './App.css';

class App extends React.Component {

  updateFilesListing() {
    this.refreshFilesListing.refreshFilesListing();
  }

  render() {

    return (
      <div className="FileUploadDownloadApp">
        <Grid container direction="column" justify="center" alignItems="center" spacing={5}>
          <Grid item xs={12}>
            <header style={{ textAlign: "center", color: "blue", fontWeight: "bold"}}>File Upload</header>
          </Grid>
          <Grid item xs={12}>
            <Paper variant="elevation">
              <UploadFile updateFilesList={this.updateFilesListing.bind(this)} />
            </Paper>
          </Grid>
          <Grid item xs={12}>
            <Paper>
             <FilesListing ref={refreshFilesListing => this.refreshFilesListing = refreshFilesListing} />
            </Paper>
          </Grid>
        </Grid>
      </div >
    );
  }
}

export default App;
