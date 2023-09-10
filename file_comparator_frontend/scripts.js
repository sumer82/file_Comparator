document
  .getElementById("compareButton")
  .addEventListener("click", function (e) {
    e.preventDefault();
    compareFoldersAndPopulateResults();
  });

function compareFoldersAndPopulateResults() {
  // Get folder paths from the input fields
  var folderPath1 = document.getElementById("folderPath1").value;
  var folderPath2 = document.getElementById("folderPath2").value;

  var formData = new FormData();
  formData.append("path1", folderPath1);
  formData.append("path2", folderPath2);

  // Make an API request to compare folders
  fetch(`http://localhost:8000/file-comparison/compare`, {
    method: "POST", // or 'GET' depending on your API
    body: formData,
  })
    .then(function (response) {
      return response.json();
    })
    .then(function (data) {
      // Process the API response and populate the dashboard with results
      console.log(data);
      //Example: Update the result containers
      var totalFoldersInBoth = document.getElementById("same");
      var foldersNotPresentInDirectory1 =
        document.getElementById("notPresentIn1");
      var foldersNotPresentInDirectory2 =
        document.getElementById("notPresentIn2");

      totalFoldersInBoth.innerText = data.totalFoldersInBoth;
      foldersNotPresentInDirectory1.innerText =
        data.foldersNotPresentInDirectory1;
      foldersNotPresentInDirectory2.innerText =
        data.foldersNotPresentInDirectory2;

      var fileNames1 = data.directory1Contents;
      var fileNames2 = data.directory2Contents;

      // Get a reference to the <ul> element
      const ul1 = document.querySelector("#column1 ul");
      const ul2 = document.querySelector("#column2 ul");

      // Loop through the file names and populate the <ul> element
      fileNames1.forEach((fileName) => {
        // Create an <li> element
        const li = document.createElement("li");

        // Create a checkbox input element
        const checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        checkbox.name = "selectedFolders"; // Set a name for the checkbox

        // Create a label element for the checkbox
        const label = document.createElement("label");
        label.textContent = fileName;
        checkbox.value = fileName;

        // Append the checkbox and label to the list item
        li.appendChild(checkbox);
        li.appendChild(label);

        // Append the <li> element to the <ul> element
        ul1.appendChild(li);
      });
      // Loop through the file names and populate the <ul> element
      fileNames2.forEach((fileName) => {
        // Create an <li> element
        const li = document.createElement("li");

        // Create a checkbox input element
        const checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        checkbox.name = "selectedFolders2"; // Set a name for the checkbox
        checkbox.value = fileName;

        // Create a label element for the checkbox
        const label = document.createElement("label");
        label.textContent = fileName;

        // Append the checkbox and label to the list item
        li.appendChild(checkbox);
        li.appendChild(label);

        // Append the <li> element to the <ul> element
        ul2.appendChild(li);
      });

      const div1 = document.getElementById("form");
      const div2 = document.getElementById("box");
      const div3 = document.getElementById("content");

      div1.style.display = "none";
      div2.style.display = "grid";
      div3.style.display = "block";

      //You can add more logic to display other results as needed
    })
    .catch(function (error) {
      console.error("Error:", error);
      // Handle errors if necessary
    });
}

// Add a click event listener to the Compare Folders button
document
  .getElementById("compareButton2")
  .addEventListener("click", compareFoldersAndPopulateResults2);

// Function to call the API and populate results
function compareFoldersAndPopulateResults2() {
  const folderPath1 = document.getElementById("folderPath1").value;
  const folderPath2 = document.getElementById("folderPath2").value;
  var folderPath1WithFolderName = null;
  var folderPath2WithFolderName = null;
  console.log(folderPath1);
  // Get all checkboxes with the name 'selectedFolders'
  const checkboxes = document.querySelectorAll(
    'input[name="selectedFolders"]:checked'
  );
  const checkboxes2 = document.querySelectorAll(
    'input[name="selectedFolders2"]:checked'
  );
  // Loop through selected checkboxes
  checkboxes.forEach((checkbox) => {
    const folderName = checkbox.value; // Folder name from the checkbox value
    folderPath1WithFolderName = `${folderPath1}\\${folderName}`;
    console.log(folderPath1WithFolderName);
  });
  checkboxes2.forEach((checkbox) => {
    const folderName = checkbox.value; // Folder name from the checkbox value
    folderPath2WithFolderName = `${folderPath2}\\${folderName}`;
    console.log(folderPath2WithFolderName);
  });

  // API endpoint to call (replace with your actual API endpoint)
  const apiUrl = `http://localhost:8000/file-comparison/compare_file?path1=${encodeURIComponent(
    folderPath1WithFolderName
  )}&path2=${encodeURIComponent(folderPath2WithFolderName)}`;

  fetch(apiUrl, { method: "POST" })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      const div1 = document.getElementById("content");
      const div2 = document.getElementById("box");
      const div3 = document.getElementById("comparisonResults");

      div1.style.display = "none";
      div2.style.display = "grid";
      div3.style.display = "block";
      displayComparisonResults(data);
    })
    .catch((error) => {
      console.error("Error fetching data:", error);
    });
}

// Function to display comparison results with styles
function displayComparisonResults(response) {
  var total_file = response.totalFilesInBoth;
  var total_diff_file = response.results.length;
  var total_diff = 0;
  const comparisonResultsDiv = document.getElementById("comparisonResults");

  response.results.forEach((result) => {
    const fileName = result.fileName;
    const differences = result.differences;

    const fileContainer = document.createElement("div");
    fileContainer.classList.add("file-container");

    const fileHeader = document.createElement("div");
    fileHeader.classList.add("file-header");
    fileHeader.textContent = `Filename: ${fileName}`;

    const fileDifferences = document.createElement("div");
    fileDifferences.classList.add("file-differences");
    differences.forEach((difference) => {
      total_diff += 1;

      const differenceDiv = document.createElement("div");
      differenceDiv.textContent = difference;
      differenceDiv.classList.add("mismatched"); // Apply mismatched style
      fileDifferences.appendChild(differenceDiv);
    });

    fileContainer.appendChild(fileHeader);
    fileContainer.appendChild(fileDifferences);

    comparisonResultsDiv.appendChild(fileContainer);
  });
  console.log(total_diff);

  var diffs = total_diff;

  var totalFoldersInBoth = document.getElementById("same");
  var foldersNotPresentInDirectory1 = document.getElementById("notPresentIn1");
  var foldersNotPresentInDirectory2 = document.getElementById("notPresentIn2");

  var totalFoldersInBoth_p = document.getElementById("same_p");
  var diff_files_p = document.getElementById("same_d");
  var total_diff = document.getElementById("same_d2");

  totalFoldersInBoth.innerText = total_file;
  totalFoldersInBoth_p.innerText = "Total Files";
  foldersNotPresentInDirectory1.innerText = total_diff_file;
  diff_files_p.innerText = "Total  Diff Files";
  foldersNotPresentInDirectory2.textContent = diffs;
  total_diff.innerText = "Total Differences";
}
