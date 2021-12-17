export enum HallplanElementType {
  seat = 'seat',
  hallway = 'hallway',
  stage = 'stage',
  exit = 'exit',
  standing = 'standing',
}
export interface AddSectionDialogData {
  color: string;
  name: string;
  price: number;
  sectors: Sector[];
  editIndex: number;
  onlyPriceEditable: boolean;
}

export interface IHallplanElement {
  added: boolean;
  removeCandidate: boolean;
  sector: number;
  type: HallplanElementType;
  withType: (type: HallplanElementType) => IHallplanElement;
  withRemoveCandidate: (removeCandidate: boolean) => IHallplanElement;
  withAdded: (added: boolean) => IHallplanElement;
  withSector: (sector: number) => IHallplanElement;
}

export class Sector {
  selected = false;

  constructor(public color: string, public name: string, private price: number) { }

  formatPrice() {
    return '€' + this.price.toFixed(2);
  }

  withSelected(b: boolean) {
    this.selected = b;
    return this;
  }

  withColor(color) {
    this.color = color;
    return this;
  }

  withPrice(price) {
    this.price = price;
    return this;
  }

  withName(name) {
    this.name = name;
    return this;
  }
}

export class HallplanElement implements IHallplanElement {
  added = false;
  removeCandidate = false;
  type = HallplanElementType.seat;
  sector = 0;

  constructor(added?) {
    this.withRemoveCandidate = this.withRemoveCandidate.bind(this);
    this.withType = this.withType.bind(this);
    this.withAdded = this.withAdded.bind(this);
    this.withSector = this.withSector.bind(this);
    if (added) {
      this.added = added;
    }
  }

  withRemoveCandidate(removeCandidate: boolean): HallplanElement {
    this.removeCandidate = removeCandidate;
    return this;
  }

  withType(type: HallplanElementType): HallplanElement {
    this.type = type;
    return this;
  }

  withAdded(added: boolean): HallplanElement {
    this.added = added;
    return this;
  }

  withSector(sector: number): HallplanElement {
    this.sector = sector;
    return this;
  }
}
