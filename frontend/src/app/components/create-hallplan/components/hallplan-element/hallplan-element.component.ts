import {AfterViewChecked, AfterViewInit, Component, HostBinding, Input, OnChanges, OnInit} from '@angular/core';
import {HallplanElement, HallplanElementType, IHallplanElement} from '../../types';

@Component({
  selector: 'app-hallplan-element',
  templateUrl: './hallplan-element.component.html',
  styleUrls: ['./hallplan-element.component.scss']
})
export class HallplanElementComponent implements OnChanges {
  @Input() add: (rowIndex: number, seatIndex: number) => void;
  @Input() remove: (rowIndex: number, seatIndex: number) => void;
  @Input() mouseOverHallplanElement: (rowIndex: number, seatIndex: number) => void;
  @Input() mouseOutHallplanElement: (rowIndex: number, seatIndex: number) => void;
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
  @Input() rows: IHallplanElement[][] = [];

  @Input() hallplanElement: HallplanElement;

  @HostBinding('class.zIndex') zIndex = false;

  constructor() {
    this.onAdd = this.onAdd.bind(this);
  }

  ngOnChanges() {
    this.onAdd();
  };

  onAdd(): void {
    console.log(this.rowIndex + ' ' + this.seatIndex);
    if (this.hallplanElement.added && this.hallplanElement.type === HallplanElementType.exit &&
      this.rows[this.rowIndex + 1][this.seatIndex].type === HallplanElementType.exit &&
      this.rows[this.rowIndex + 1][this.seatIndex].added &&
      ((this.rows[this.rowIndex + 1][this.seatIndex + 1].type === HallplanElementType.exit &&
        this.rows[this.rowIndex + 1][this.seatIndex + 1].added) ||
        (this.rows[this.rowIndex + 1][this.seatIndex - 1].type === HallplanElementType.exit &&
          this.rows[this.rowIndex + 1][this.seatIndex - 1].added))) {
      console.log('zIndex true');
      this.zIndex = true;
    } else {
      console.log('zIndex false');
      this.zIndex = false;
    }
  }

  hallplanElementClick() {
    if (this.hallplanElement.added) {
      this.remove(this.rowIndex, this.seatIndex);
    } else {
      this.add(this.rowIndex, this.seatIndex);
    }
  }

  isOutside = () => this.outsideTop || this.outsideLeft || this.outsideBottom || this.outsideRight;

  getToolTip = () => 'This removes the outer row';

  getSeatClass() {
    let style = `hallplan-element-container hallplan-element ${this.hallplanElement.type}`;
    if (this.hallplanElement.added) {
      style += ' added';
    } else {
      style += ' removed';
    }
    if (this.hallplanElement.removeCandidate) {
      style += ' remove-candidate';
    }

    const rightElement = this.rows[this.rowIndex][this.seatIndex + 1];
    const leftElement = this.rows[this.rowIndex][this.seatIndex - 1];
    const topElement = this.rows[this.rowIndex - 1][this.seatIndex];
    const bottomElement = this.rows[this.rowIndex + 1][this.seatIndex];
    if (this.hallplanElement.type === HallplanElementType.exit && this.hallplanElement.added) {

      if ((leftElement.added && leftElement.type === HallplanElementType.exit) ||
        (rightElement.added && rightElement.type === HallplanElementType.exit)) {
        style += ' rotated';
      }
    }
    if (leftElement.type !== this.hallplanElement.type || !leftElement.added) {
      style += ' left';
    }
    if (rightElement.type !== this.hallplanElement.type || !rightElement.added) {
      style += ' right';
    }
    if (bottomElement.type !== this.hallplanElement.type || !bottomElement.added) {
      style += ' bottom';
    }
    if (topElement.type !== this.hallplanElement.type || !topElement.added) {
      style += ' top';
    }

    return style;
  }
}
