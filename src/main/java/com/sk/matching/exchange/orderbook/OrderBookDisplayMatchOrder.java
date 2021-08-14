package com.sk.matching.exchange.orderbook;

import com.sk.matching.exchange.order.GenOrder;
import lombok.extern.log4j.Log4j2;

import java.text.DecimalFormat;
import java.util.*;

@Log4j2
public class OrderBookDisplayMatchOrder implements OrderBookDisplay {

    private static final OrderBookDisplayMatchOrder DISPLAY_MATCH_ORDER = new OrderBookDisplayMatchOrder();
    public static OrderBookDisplayMatchOrder getInstance() {
        return DISPLAY_MATCH_ORDER;
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Prohibited to be cloned");
    }

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###########.00##########");
    private static final DecimalFormat DECIMAL_TO_INT_FORMAT = new DecimalFormat("###########");

    @Override
    public String printFormat(OrderBook orderBook) {
        final StringBuilder sb = new StringBuilder("\n=================== ORDER BOOK ===================\n");
        sb.append("\nequitySymbol=").append(orderBook.getSymbol())
                .append("-hashCode=").append(orderBook.getSymbol().hashCode())
                .append("\n")
                .append("ID\tSide\tTime\t\t\tQty\t\tPrice\tQty\t\tTime\t\t\t\tSide")
                .append(formatAsk(orderBook))
                .append(formatBid(orderBook))
                .append("\n")
                .append("\n=================== END of ORDER BOOK ===================");
        return sb.toString();

    }

    private String formatAsk(OrderBook orderBook) {
        StringBuilder sb = new StringBuilder("\n");
        List<GenOrder> res = new ArrayList<>();
        for ( Map.Entry<Double, List<GenOrder>> entry : orderBook.getAskOrderSortedMap().entrySet() ) {
            List<GenOrder> genOrderList = entry.getValue();
            res.addAll(genOrderList);
        }
        Collections.reverse(res);
        for(GenOrder genOrder : res) {
            sb.append(genOrder.getOrderId()).append("\t")
                    .append("    \t    \t   \t\t\t\t")
                    .append(DECIMAL_FORMAT.format(genOrder.getOrdPx())).append("\t")
                    .append(DECIMAL_TO_INT_FORMAT.format(genOrder.getLeavesQty())).append("\t")
                    .append(genOrder.getReceivedTS()).append("\t\t")
                    .append(genOrder.getSide());
            sb.append("\n");
        }
        return sb.toString();
    }

    private String formatBid(OrderBook orderBook) {
        StringBuilder sb = new StringBuilder();
        List<GenOrder> res = new ArrayList<>();
        for ( Map.Entry<Double, List<GenOrder>> entry : orderBook.getBidOrderSortedMap().entrySet() ) {
            List<GenOrder> genOrderList = new ArrayList<>(entry.getValue());
            Collections.reverse(genOrderList);
            res.addAll(genOrderList);
        }
        Collections.reverse(res);
        for(GenOrder genOrder : res) {
            sb.append(genOrder.getOrderId()).append("\t")
                    .append(genOrder.getSide()).append("\t")
                    .append(genOrder.getReceivedTS()).append("\t")
                    .append(DECIMAL_TO_INT_FORMAT.format(genOrder.getLeavesQty())).append("\t\t")
                    .append(DECIMAL_FORMAT.format(genOrder.getOrdPx())).append("\t")
                    .append("  \t    \t    \t   \t");
            sb.append("\n");
        }

        return sb.toString();
    }

}
