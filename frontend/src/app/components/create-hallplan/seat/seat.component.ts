import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-seat',
  templateUrl: './seat.component.html',
  styleUrls: ['./seat.component.scss']
})
export class SeatComponent implements OnInit {
  @Input() added: boolean;
  @Input() removeCandidate: boolean;
  @Input() add: (rowIndex: number, seatIndex: number) => void;
  @Input() remove: (rowIndex: number, seatIndex: number) => void;
  @Input() mouseOverSeat: (rowIndex: number, seatIndex: number) => void;
  @Input() mouseOutSeat: (rowIndex: number, seatIndex: number) => void;
  @Input() rowIndex: number;
  @Input() seatIndex: number;

  @Input() smallerThanMinRowsNumber: boolean;
  @Input() smallerThanMinSeatsNumber: boolean;

  @Input() outsideTop: boolean;
  @Input() outsideLeft: boolean;
  @Input() outsideBottom: boolean;
  @Input() outsideRight: boolean;

  @Input() isLastTop: boolean;
  @Input() isLastLeft: boolean;
  @Input() isLastBottom: boolean;
  @Input() isLastRight: boolean;

  ngOnInit(): void {
  }

  seatClick() {
    if (this.added) {
      this.remove(this.rowIndex, this.seatIndex);
    } else {
      this.add(this.rowIndex, this.seatIndex);
    }
  }

  isOutside = () => this.outsideTop || this.outsideLeft || this.outsideBottom || this.outsideRight;

  getToolTip = () => 'This removes the outer row';

  getSeatClass() {
    return `seat-container seat test ${this.added ? 'added' : 'removed'} ${this.removeCandidate ? 'remove-candidate' : ''}`;
  }
}
