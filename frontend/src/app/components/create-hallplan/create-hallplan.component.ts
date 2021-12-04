import {AfterContentInit, Component, OnInit} from '@angular/core';

interface Seat {
  added: boolean;
  removeCandidate: boolean;
}

@Component({
  selector: 'app-create-hallplan',
  templateUrl: './create-hallplan.component.html',
  styleUrls: ['./create-hallplan.component.scss']
})
export class CreateHallplanComponent implements OnInit {
  rows: Seat[][] = [];
  defaultRowsNumber = 10;
  defaultSeatsNumber = 10;
  minRowsNumber = 8;
  minSeatsNumber = 8;

  constructor() {
    this.addSeat = this.addSeat.bind(this);
    this.removeSeat = this.removeSeat.bind(this);
    this.mouseOverSeat = this.mouseOverSeat.bind(this);
    this.mouseOutSeat = this.mouseOutSeat.bind(this);
  }

  ngOnInit(): void {
    const rows: Seat[][] = [];
    for (let rowIndex = 0; rowIndex < this.defaultRowsNumber; rowIndex++) {
      const row: Seat[] = [];
      for (let seatIndex = 0; seatIndex < this.defaultSeatsNumber; seatIndex++) {
        if (seatIndex === 0 || rowIndex === 0 || rowIndex === this.defaultRowsNumber - 1 || seatIndex === this.defaultSeatsNumber - 1) {
          row.push({
            added: false,
            removeCandidate: false,
          });
        } else {
          row.push({
            added: true,
            removeCandidate: false,
          });
        }
      }
      this.rows.push(row);
    }
  }

  createRow(added: boolean): Seat[] {
    const row: Seat[] = [];
    for (const seat of this.rows[0]) {
      row.push({
        added,
        removeCandidate: false,
      });
    }
    return row;
  }

  addSeat(rowIndex: number, seatIndex: number) {
    this.rows[rowIndex][seatIndex].added = true;
    if (rowIndex === 0) {
      this.rows = [this.createRow(false)].concat(this.rows);
    }
    if (seatIndex === 0) {
      for (let i = 0; i < this.rows.length; i++) {
        this.rows[i] = [{
          added: false,
          removeCandidate: false,
        }].concat(this.rows[i]);
      }
    }
    if (rowIndex === this.rows.length - 1) {
      this.rows = this.rows.concat([this.createRow(false)]);
    }
    if (seatIndex === this.rows[0].length - 1) {
      for (let i = 0; i < this.rows.length; i++) {
        this.rows[i] = this.rows[i].concat([{
          added: false,
          removeCandidate: false,
        }]);
      }
    }
  }

  allSeatsRemoved(rows: Seat[]): boolean {
    for (const row of rows) {
      if (row.added) {
        return false;
      }
    }
    return true;
  }

  isLastTopSeat = (rowIndex, seatIndex, indent = 1) => rowIndex === indent && this.allSeatsRemoved(
    this.rows[rowIndex].filter((unused, index) => index !== seatIndex)
  );

  isLastLeftSeat = (rowIndex, seatIndex, indent = 1) => seatIndex === indent && this.allSeatsRemoved(
    this.rows.filter((unused, index) => index !== rowIndex).map(row => row[seatIndex])
  );

  isLastBottomSeat = (rowIndex, seatIndex, indent = 1) => rowIndex === this.rows.length - 1 - indent && this.allSeatsRemoved(
    this.rows[rowIndex].filter((unused, index) => index !== seatIndex));

  isLastRightSeat = (rowIndex, seatIndex, indent = 1) => seatIndex === this.rows[0].length - 1 - indent && this.allSeatsRemoved(
    this.rows.filter((unused, index) => index !== rowIndex).map(row => row[seatIndex]));

  mouseOverSeat(rowIndex: number, seatIndex: number, indent = 1) {
    if (this.rows.length > this.minRowsNumber && this.isLastTopSeat(rowIndex, seatIndex, indent)) {
      this.rows[rowIndex].map((seat) => {
        seat.removeCandidate = true;
        return seat;
      });
      this.mouseOverSeat(rowIndex + 1, 0, indent + 1);
    }
    if (this.rows[0].length > this.minSeatsNumber && this.isLastLeftSeat(rowIndex, seatIndex, indent)) {
      this.rows = this.rows.map(row => {
        row[seatIndex].removeCandidate = true;
        return row;
      });
      this.mouseOverSeat(0, seatIndex + 1, indent + 1);
    }
    if (this.rows.length > this.minRowsNumber && this.isLastBottomSeat(rowIndex, seatIndex, indent)) {
      this.rows[rowIndex].map((seat) => {
        seat.removeCandidate = true;
        return seat;
      });
      this.mouseOverSeat(rowIndex - 1, this.rows[0].length - 1, indent + 1);
    }
    if (this.rows[0].length > this.minSeatsNumber && this.isLastRightSeat(rowIndex, seatIndex, indent)) {
      this.rows = this.rows.map(row => {
        row[seatIndex].removeCandidate = true;
        return row;
      });
      this.mouseOverSeat(this.rows.length - 1, seatIndex - 1, indent + 1);
    }
  }

  mouseOutSeat(rowIndex: number, seatIndex: number) {
    this.rows.map(row => row.map(seat => {
      seat.removeCandidate = false;
      return seat;
    }));
  }

  removeSeat(rowIndex: number, seatIndex: number, indent = 1) {
    let removed = false;
    this.rows[rowIndex][seatIndex].added = false;
    if (this.rows.length > this.minRowsNumber && this.isLastTopSeat(1, 0)) {
      removed = true;
      this.rows = this.rows.slice(1);
      this.removeSeat(1, 0);
    }
    if (this.rows[0].length > this.minSeatsNumber && this.isLastLeftSeat(0, 1)) {
      removed = true;
      this.rows = this.rows.map(row => row.slice(1));
      this.removeSeat(0, 1);
    }
    if (this.rows.length > this.minRowsNumber && this.isLastBottomSeat(this.rows.length - 2, this.rows[0].length - 1)) {
      removed = true;
      this.rows = this.rows.slice(0, this.rows.length - 1);
      this.removeSeat(this.rows.length - 2, this.rows[0].length - 1);
    }
    if (this.rows[0].length > this.minSeatsNumber && this.isLastRightSeat(this.rows.length - 1, this.rows[0].length - 2)) {
      removed = true;
      this.rows = this.rows.map(row => row.slice(0, row.length - 1));
      this.removeSeat(this.rows.length - 1, this.rows[0].length - 2);
    }
  }
}
