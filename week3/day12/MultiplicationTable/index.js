let mainContainer = document.getElementById("b1");

let title = document.createElement("h1");
title.textContent = "Multiplication Table";
title.style.textAlign = "center";
mainContainer.appendChild(title);

let activeCell = null;

class MultiplicationTable {
    constructor() {
        this.tableElement = this.buildTable();
    }

    highlightOnHover(event) {
        if (activeCell !== event.target) {
            event.target.style.backgroundColor = "#eade91";
        }
    }

    removeHighlight(event) {
        if (activeCell !== event.target) {
            event.target.style.backgroundColor = "white";
        }
    }

    createTableCell(content, cellTag) {
        let tableCell = document.createElement(cellTag);
        tableCell.innerHTML = content;
        tableCell.style.width = "50px";
        tableCell.style.height = "50px";
        tableCell.style.border = "1px solid black";

        tableCell.addEventListener("mouseover", this.highlightOnHover.bind(this));
        tableCell.addEventListener("mouseout", this.removeHighlight.bind(this));

        return tableCell;
    }

    handleCellClick(event) {
        const selectedCell = event.target;
        if (selectedCell.tagName === "TD" || selectedCell.tagName === "TH") {
            if (activeCell) {
                activeCell.style.backgroundColor = "white";
            }

            selectedCell.style.backgroundColor = "green";
            activeCell = selectedCell;
        }
    }

    buildTable() {
        let table = document.createElement("table");
        for (let row = 1; row <= 10; row++) {
            let tableRow = document.createElement("tr");
            for (let col = 1; col <= 10; col++) {
                let cellTag = "td";
                if (row === 1 || col === 1) {
                    cellTag = "th";
                }
                tableRow.appendChild(this.createTableCell(row * col, cellTag));
            }
            table.appendChild(tableRow);
        }
        table.style.textAlign = "center";
        table.style.margin = "0 auto";

        table.addEventListener("click", this.handleCellClick.bind(this));

        return table;
    }
}

let multiplicationTable = new MultiplicationTable();
mainContainer.appendChild(multiplicationTable.tableElement);

let backgroundColors = ["blue", "purple", "red"];
let colorIndex = 0;

function cycleBackgroundColor() {
    if (colorIndex >= backgroundColors.length) {
        colorIndex = 0;
    }
    mainContainer.style.backgroundColor = backgroundColors[colorIndex];
    colorIndex++;
}

let backgroundToggleButton = document.createElement("button");
backgroundToggleButton.textContent = "CLICK HERE";
backgroundToggleButton.style.position = "relative";
backgroundToggleButton.style.left = "50%";

backgroundToggleButton.addEventListener("click", function () {
    setInterval(cycleBackgroundColor, 100);
});

mainContainer.appendChild(backgroundToggleButton);
