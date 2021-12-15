import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ArtistPerformancesComponent } from './artist-performances.component';

describe('ArtistPerformancesComponent', () => {
  let component: ArtistPerformancesComponent;
  let fixture: ComponentFixture<ArtistPerformancesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ArtistPerformancesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ArtistPerformancesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
