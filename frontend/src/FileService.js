const ROOT_URL = "http://localhost:8080/api/v1/files";

class FileService {

  async postUploadFile(data) {

    let options  = { method: 'POST', body: data}
    let response = await fetch(`${ROOT_URL + "/upload"}`, options);
    return response;
  }

   async getDownloadFile(filename) {

    let response = await fetch(`${ROOT_URL}/download?filename=`+ filename);
    return response;
  }

  async getFilesListing() {

    let response = await fetch(`${ROOT_URL}`);
    return response;
  }
}

export default new FileService();