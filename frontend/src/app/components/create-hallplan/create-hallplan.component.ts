import {Component, OnInit} from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import {HallplanElementType, IHallplanElement, HallplanElement, Sector} from './types';
import {MatDialog} from '@angular/material/dialog';
import {AddSectionDialogComponent} from './components/add-section-dialog/add-section-dialog.component';
import {HallAddRequest} from '../../dtos/hall-add-request';
import {EventPlaceService} from '../../services/event-place.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-create-hallplan',
  templateUrl: './create-hallplan.component.html',
  styleUrls: ['./create-hallplan.component.scss']
})
export class CreateHallplanComponent implements OnInit {
  rows: IHallplanElement[][] = [];
  sectors: Sector[] = [
    new Sector('#ffffff', 'default', 30.00),
    new Sector('#ffff00', 'test', 32.00),
  ];

  defaultRowsNumber = 10;
  defaultSeatsNumber = 10;
  minRowsNumber = 8;
  minSeatsNumber = 8;

  radioControl = new FormControl(HallplanElementType.seat);
  editModes = HallplanElementType;
  page = 1;
  nameControl = new FormControl('', [Validators.required]);
  eventPlaceId: string;

  constructor(public dialog: MatDialog, private eventPlaceService: EventPlaceService,
              private route: ActivatedRoute) {
    this.addSeat = this.addSeat.bind(this);
    this.removeSeat = this.removeSeat.bind(this);
    this.mouseOverHallplanElement = this.mouseOverHallplanElement.bind(this);
    this.mouseOutHallplanElement = this.mouseOutHallplanElement.bind(this);
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.eventPlaceId = params['id'];
    });
    const rows: IHallplanElement[][] = [];
    for (let rowIndex = 0; rowIndex < this.defaultRowsNumber; rowIndex++) {
      const row: IHallplanElement[] = [];
      for (let seatIndex = 0; seatIndex < this.defaultSeatsNumber; seatIndex++) {
        if (seatIndex === 0 || rowIndex === 0 || rowIndex === this.defaultRowsNumber - 1 || seatIndex === this.defaultSeatsNumber - 1) {
          row.push(new HallplanElement());
        } else {
          row.push(new HallplanElement(true));
        }
      }
      this.rows.push(row);
    }
  }

  createRow(added: boolean): IHallplanElement[] {
    const row: IHallplanElement[] = [];
    for (const seat of this.rows[0]) {
      row.push(new HallplanElement(added));
    }
    return row;
  }

  addSeat(rowIndex: number, seatIndex: number) {
    console.log(`addSeat(${rowIndex}, ${seatIndex});`);
    this.rows[rowIndex][seatIndex] = {...this.rows[rowIndex][seatIndex].withType(this.radioControl.value).withAdded(true)};
    if (rowIndex === 0) {
      this.rows = [this.createRow(false)].concat(this.rows);
    }
    if (seatIndex === 0) {
      for (let i = 0; i < this.rows.length; i++) {
        this.rows[i] = [new HallplanElement() as IHallplanElement].concat(this.rows[i]);
      }
    }
    if (rowIndex === this.rows.length - 1) {
      this.rows = this.rows.concat([this.createRow(false)]);
    }
    if (seatIndex === this.rows[0].length - 1) {
      for (let i = 0; i < this.rows.length; i++) {
        this.rows[i] = this.rows[i].concat([new HallplanElement()]);
      }
    }
  }

  allSeatsRemoved(rows: IHallplanElement[]): boolean {
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

  mouseOverHallplanElement(rowIndex: number, seatIndex: number, indent = 1) {
    if (this.rows.length > this.minRowsNumber && this.isLastTopSeat(rowIndex, seatIndex, indent)) {
      this.rows[rowIndex].map((seat, ind) => seat.withRemoveCandidate(true));
      this.mouseOverHallplanElement(rowIndex + 1, 0, indent + 1);
    }
    if (this.rows[0].length > this.minSeatsNumber && this.isLastLeftSeat(rowIndex, seatIndex, indent)) {
      this.rows.map(row => {
        row[seatIndex].withRemoveCandidate(true);
        return row;
      });
      this.mouseOverHallplanElement(0, seatIndex + 1, indent + 1);
    }
    if (this.rows.length > this.minRowsNumber && this.isLastBottomSeat(rowIndex, seatIndex, indent)) {
      this.rows[rowIndex].map((seat) => seat.withRemoveCandidate(true));
      this.mouseOverHallplanElement(rowIndex - 1, this.rows[0].length - 1, indent + 1);
    }
    if (this.rows[0].length > this.minSeatsNumber && this.isLastRightSeat(rowIndex, seatIndex, indent)) {
      this.rows.map(row => {
        row[seatIndex].withRemoveCandidate(true);
        return row;
      });
      this.mouseOverHallplanElement(this.rows.length - 1, seatIndex - 1, indent + 1);
    }
  }

  mouseOutHallplanElement(rowIndex: number, seatIndex: number) {
    this.rows.map(row => row.map(seat => seat.withRemoveCandidate(false)));
  }

  removeSeat(rowIndex: number, seatIndex: number, indent = 1) {
    let removed = false;
    this.rows[rowIndex][seatIndex] = this.rows[rowIndex][seatIndex].withAdded(false);
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

  next() {
    this.page++;
  }

  addSector() {
    const dialogRef = this.dialog.open(AddSectionDialogComponent, {
      width: '250px',
      data: {
        color: '#00ffff',
        name: '',
        price: 0.0
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      console.log(result);
      this.sectors.push(new Sector(result.color, result.name, result.price));
    });
  }

  setSelectedSection(i: number) {
    this.sectors.map((sector, index) => sector.withSelected(i === index));
  }

  finish() {
    const hallRequest = new HallAddRequest(
      this.nameControl.value,
      this.rows.filter(
        (row, index) => index !== 0 && index !== this.rows.length - 1
      ).map(row => row.filter(
        (seat, index) => index !== 0 && index !== row.length - 1)
      ),
      this.sectors,
    );
    this.eventPlaceService.addHall(this.eventPlaceId, hallRequest).subscribe({
      next: () => {
        console.log('success');
      },
      error: (error) => {

      }
    });
  }
}
