import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OrderValidation } from 'src/app/dtos/OrderValidation';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-validate-order',
  templateUrl: './validate-order.component.html',
  styleUrls: ['./validate-order.component.scss']
})
export class ValidateOrderComponent implements OnInit {
  orderValidation: OrderValidation;

  constructor(private route: ActivatedRoute, private orderService: OrderService) { }

  ngOnInit(): void {
    let orderId: number;
    let hash: string;
    this.route.paramMap.subscribe( paramMap => {
      orderId = Number(paramMap.get('id'));
    });
    this.route.queryParamMap.subscribe( querParamMap => {
      hash = querParamMap.get('hash');
    });
    this.orderService.validateOrder(orderId, hash).subscribe({
      next: next => {
        console.log(next);
        this.orderValidation = next;
      },
      error: () => {
        this.orderValidation = {
          valid: false,
          comment: 'There was an error when validating the Ticket!'
        } as OrderValidation;
      }
    });
  }

  renderDate(dt: Date) {
    const date = new Date(dt);
    console.log(dt);
    const d = date.getDate();
    const m = date.getMonth() + 1;
    const y = date.getFullYear();
    const h = date.getHours();
    const min = date.getMinutes();
    return d + '.' + m + '.' + y + ' ' + h + ':' + min;
  }
}
