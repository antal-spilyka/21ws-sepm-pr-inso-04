import {Component, HostBinding, Input, OnChanges} from '@angular/core';
import {HallplanElementType, IHallplanElement, Sector} from '../../types';

@Component({
  selector: 'app-hallplan-element',
  templateUrl: './hallplan-element.component.html',
  styleUrls: ['./hallplan-element.component.scss']
})
export class HallplanElementComponent implements OnChanges {
  @Input() add: (rowIndex: number, seatIndex: number) => void;
  @Input() remove: (rowIndex: number, seatIndex: number) => void;
  @Input() book?: (rowIndex: number, seatIndex: number) => void;
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

  @Input() bookMode: boolean;
  @Input() disabled: boolean;
  @Input() alreadyUsed: boolean;
  @Input() rows: IHallplanElement[][] = [];
  @Input() sectors: Sector[];

  @Input() hallplanElement: IHallplanElement;

  @HostBinding('class.zIndex') zIndex = false;

  ngOnChanges() {
  };

  hallplanElementClick() {
    console.log(this.bookMode);
    if (!this.disabled) {
      if (this.hallplanElement.added) {
        this.remove(this.rowIndex, this.seatIndex);
      } else {
        this.add(this.rowIndex, this.seatIndex);
      }
    } else if (this.bookMode && this.hallplanElement.type === HallplanElementType.seat) {
      if (!this.alreadyUsed) {
        this.book(this.rowIndex, this.seatIndex);
      }
    } else {
      const sector = this.sectors.findIndex(sector2 => sector2.selected);
      if (sector !== -1) {
        this.hallplanElement.withSector(sector);
      }
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
    if (this.hallplanElement.removeCandidate && !this.disabled) {
      style += ' remove-candidate';
    }

    if (this.disabled) {
      style += ' disabled';
    }

    const rightElement = this.rows[this.rowIndex][this.seatIndex + 1];
    const leftElement = this.rows[this.rowIndex][this.seatIndex - 1];
    const topElement = this.rows[this.rowIndex - 1][this.seatIndex];
    const bottomElement = this.rows[this.rowIndex + 1][this.seatIndex];

    const topLeftElement = this.rows[this.rowIndex - 1][this.seatIndex - 1];
    const topRightElement = this.rows[this.rowIndex - 1][this.seatIndex + 1];
    const bottomRightElement = this.rows[this.rowIndex + 1][this.seatIndex + 1];
    const bottomLeftElement = this.rows[this.rowIndex + 1][this.seatIndex - 1];

    if (this.hallplanElement.booked) {
      style += ' booked';
    }

    if (this.alreadyUsed) {
      style += ' already-used';
    }

    if (leftElement.type === this.hallplanElement.type && leftElement.added) {
      style += ' left';
    }
    if (rightElement.type === this.hallplanElement.type && rightElement.added) {
      style += ' right';
    }
    if (bottomElement.type === this.hallplanElement.type && bottomElement.added) {
      style += ' bottom';
    }
    if (topElement.type === this.hallplanElement.type && topElement.added) {
      style += ' top';
    }

    if (topLeftElement.type === this.hallplanElement.type && topLeftElement.added) {
      style += ' top-left';
    }
    if (topRightElement.type === this.hallplanElement.type && topRightElement.added) {
      style += ' top-right';
    }
    if (bottomRightElement.type === this.hallplanElement.type && bottomRightElement.added) {
      style += ' bottom-right';
    }
    if (bottomLeftElement.type === this.hallplanElement.type && bottomLeftElement.added) {
      style += ' bottom-left';
    }

    return style;
  }

  getBackground() {
    if (!this.hallplanElement.added || this.hallplanElement.type !== HallplanElementType.seat) {
      return '';
    }
    return 'background: ' + this.sectors[this.hallplanElement.sector].color;
  }
}
